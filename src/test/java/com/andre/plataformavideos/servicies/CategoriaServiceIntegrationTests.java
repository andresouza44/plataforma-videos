package com.andre.plataformavideos.servicies;

import com.andre.plataformavideos.dto.CategoriaDto;
import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.repositories.CategoriaRepository;
import com.andre.plataformavideos.service.CategoriaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class CategoriaServiceIntegrationTests {

    @Autowired
    CategoriaService service;

    @Autowired
    CategoriaRepository repository;

    private Long existId;
    private Long nonExistId;
    private Integer countTotalCategorias;
    private Categoria categoria;
    private CategoriaDto categoriaDto;

    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExistId = 1000L;
        countTotalCategorias = 3;

    }

    @Test
    public void findAllShouldReturnCategoriaDtoList(){
        List<CategoriaDto> dtos = service.findAllCategorias();

        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(countTotalCategorias, dtos.size());
        Assertions.assertEquals("Educativos", dtos.get(0).getTitulo());


    }
}
