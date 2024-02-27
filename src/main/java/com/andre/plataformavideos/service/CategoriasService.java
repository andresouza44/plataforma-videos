package com.andre.plataformavideos.service;

import com.andre.plataformavideos.dto.CategoriaDto;
import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.exceptions.CategoriaExisteException;
import com.andre.plataformavideos.exceptions.ResourceNotFoundException;
import com.andre.plataformavideos.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriasService {

    @Autowired
    private CategoriaRepository repository;

    @Transactional(readOnly = true)
    public List<CategoriaDto> findAllCategorias(){
        List<Categoria> categorias = repository.findAll();
        return  categorias.stream()
                  .map(CategoriaDto::new)
                  .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaDto FindById(Long id){
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria not found with id: "+ id));
        return   new CategoriaDto(categoria);

    }

    public CategoriaDto createCategoria (CategoriaDto dto){
        isCategoriaExist(dto.getTitulo());
        Categoria categoria = new Categoria();
        categoria.setTitulo(dto.getTitulo());
        categoria.setCor(dto.getCor());

        repository.save(categoria);
        return  new CategoriaDto(categoria);

    }

    @Transactional
    public CategoriaDto updateCategoria (Long id, CategoriaDto dto) {

        try {
            Categoria categoria = repository.getReferenceById(id);

            if (dto.getTitulo() != null) {
                isCategoriaExist(dto.getTitulo());
                categoria.setTitulo(dto.getTitulo());
            }
            if(dto.getCor() != null) categoria.setCor(dto.getCor());

            repository.save(categoria);
            return new CategoriaDto(categoria);

        }catch (EntityNotFoundException e){
            throw  new CategoriaExisteException("Categoria not found with id : " + id);
        }
    }
    private  void isCategoriaExist (String titulo ){
        List<CategoriaDto> listaCategoriaCadastrada = findAllCategorias();

        Optional<CategoriaDto> categoriaExistente = listaCategoriaCadastrada.stream()
                .filter(categoria -> categoria.getTitulo()
                        .equalsIgnoreCase(titulo))
                .findFirst();

        if(categoriaExistente.isPresent()){
            throw new CategoriaExisteException("Categoria " + titulo + " já existe no cadastro");
        }

    }

}
