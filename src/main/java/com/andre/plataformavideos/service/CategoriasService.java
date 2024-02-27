package com.andre.plataformavideos.service;

import com.andre.plataformavideos.dto.CategoriaDto;
import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.exceptions.CategoriaNotFoundException;
import com.andre.plataformavideos.exceptions.ResourceNotFoundException;
import com.andre.plataformavideos.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        isCategoriaTituloExist(dto.getTitulo());
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
                isCategoriaTituloExist(dto.getTitulo());
                categoria.setTitulo(dto.getTitulo());
            }
            if(dto.getCor() != null) categoria.setCor(dto.getCor());

            repository.save(categoria);
            return new CategoriaDto(categoria);

        }catch (EntityNotFoundException e){
            throw  new CategoriaNotFoundException("Categoria not found with id : " + id);
        }
    }

    public void deleteById(Long id){
        if(!repository.existsById(id)){
            throw new CategoriaNotFoundException("Categoria not found with id : " + id);
        }
       try{
        repository.deleteById(id);

       }catch (DataIntegrityViolationException e){
           throw new DataIntegrityViolationException(e.getMessage());
       }
    }


    private  void isCategoriaTituloExist(String titulo ){
        List<CategoriaDto> listaCategoriaCadastrada = findAllCategorias();

        Optional<CategoriaDto> categoriaExistente = listaCategoriaCadastrada.stream()
                .filter(categoria -> categoria.getTitulo()
                        .equalsIgnoreCase(titulo))
                .findFirst();

        if(categoriaExistente.isPresent()){
            throw new CategoriaNotFoundException("Categoria " + titulo + " já existe no cadastro");
        }

    }

}
