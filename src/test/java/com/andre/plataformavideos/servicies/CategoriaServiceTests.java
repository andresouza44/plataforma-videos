package com.andre.plataformavideos.servicies;

import com.andre.plataformavideos.dto.CategoriaDto;
import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.exceptions.CategoriaNotFoundException;
import com.andre.plataformavideos.exceptions.ResourceNotFoundException;
import com.andre.plataformavideos.repositories.CategoriaRepository;
import com.andre.plataformavideos.service.CategoriaService;
import com.andre.plataformavideos.tests.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CategoriaServiceTests {

    @InjectMocks
    private CategoriaService service;

    @Mock
    private CategoriaRepository repository;

    private Long existId;
    private Long nonExistId;
    private Long dependentId;
    private Integer countCategoriaNumber;
    private Categoria categoria;
    private CategoriaDto categoriaDto;

    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExistId = 2L;
        dependentId = 3L;
        countCategoriaNumber = 3;
        categoria = Factory.creatCategoria();
        categoriaDto = Factory.createCategoriaDTO();

        when(repository.findAll()).thenReturn(List.of(categoria));
        when(repository.findById(existId)).thenReturn(Optional.of(categoria));
        when(repository.findById(nonExistId)).thenReturn(Optional.empty());

        when(repository.existsById(existId)).thenReturn(true);
        when(repository.existsById(nonExistId)).thenReturn(false);
        when(repository.existsById(dependentId)).thenReturn(true);

        when(repository.getReferenceById(existId)).thenReturn(categoria);
        when(repository.getReferenceById(nonExistId)).thenThrow(CategoriaNotFoundException.class);


        Mockito.doNothing().when(repository).deleteById(existId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    public void findAllShouldReturnCategorias (){
        List<Categoria> categorias = repository.findAll();

        Assertions.assertFalse(categorias.isEmpty());
        Mockito.verify(repository).findAll();
    }

    @Test
    public void findByIdShouldReturnCategoriaDtoWhenIdExist(){
      CategoriaDto categoriaDto = service.findById(existId);

      Assertions.assertNotNull(categoriaDto);
      Mockito.verify(repository).findById(existId);

    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class,()-> {
            service.findById(nonExistId);
        });
    }

    @Test
    public void deleteShouldDeleteWhenIdExist(){
        Assertions.assertDoesNotThrow(()->{
            service.deleteById(existId);
        });

        Mockito.verify(repository).existsById(existId);
        Mockito.verify(repository).deleteById(existId);
    }

    @Test
    public void deleteShouldTrowCategoriaNotFoundExceptionWhenIdDoesNotExist(){

        Assertions.assertThrows(CategoriaNotFoundException.class, ()->{
            service.deleteById(nonExistId);
        });
        Mockito.verify(repository).existsById(nonExistId);


    }

    @Test
    public void deleteShouldThrowDataIntegrityViolationExceptionWhenDependentId(){
        Assertions.assertThrows(DataIntegrityViolationException.class , () ->{
           service.deleteById(dependentId);
        });
        Mockito.verify(repository).existsById(dependentId);
        Mockito.verify(repository).deleteById(dependentId);

    }

    @Test
    public void updateShouldReturnCategoriaDtoWhenIdExist(){
        CategoriaDto dto = service.updateCategoria(existId,categoriaDto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(existId, dto.getId());
        Assertions.assertEquals("Educativos", dto.getTitulo());
        Assertions.assertEquals("#00FFFF", dto.getCor());

        Mockito.verify(repository).getReferenceById(existId);

    }

    @Test
    public void updateShouldReturnCategoriaNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(CategoriaNotFoundException.class,() ->{
            service.updateCategoria(nonExistId, categoriaDto);
        });

        Mockito.verify(repository).getReferenceById(nonExistId);

    }
}

