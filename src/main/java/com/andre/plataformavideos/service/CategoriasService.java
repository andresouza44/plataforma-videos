package com.andre.plataformavideos.service;

import com.andre.plataformavideos.dto.CategoriaDto;
import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.exceptions.CategoriaExisteteException;
import com.andre.plataformavideos.exceptions.ResourceNotFoundException;
import com.andre.plataformavideos.repositories.CategoriaRepository;
import jakarta.persistence.Entity;
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
       List<CategoriaDto> listaCategoriaCadastrada = this.findAllCategorias();
       listaCategoriaCadastrada.forEach(categoria -> {
           if(categoria.getTitulo().equals(dto.getTitulo())){
               throw  new RuntimeException("Categoria " + dto.getTitulo() + " já existe no cadastro");
       }
       });
        Optional<CategoriaDto> categoriaExistente = listaCategoriaCadastrada.stream()
                .filter(categoria -> categoria.getTitulo().equals(dto.getTitulo()))
                .findFirst();

        if(categoriaExistente.isPresent()){
            throw new CategoriaExisteteException("Categoria " + dto.getTitulo() + " já existe no cadastro");
        }

        Categoria categoria = new Categoria();
        categoria.setTitulo(dto.getTitulo());
        categoria.setCor(dto.getCor());

        repository.save(categoria);
        return  new CategoriaDto(categoria);

    }
}
