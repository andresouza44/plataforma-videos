package com.andre.plataformavideos.repositories;

import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.tests.factory.Factory;
import jakarta.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

@DataJpaTest
public class CategoriaRepositiryTests {

    @Autowired
    CategoriaRepository repository;

    private Long existId;
    private Long nonExistId;
    private Integer countTotalCategorias;

    private   Categoria categoria;

    private ValidatorFactory validatorFactory;
    private Validator validator;


    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExistId = 1000L;
        countTotalCategorias = 3;
        categoria = Factory.creatCategoria();

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

    }

    @Test
    public void deleteShouldDeleteWhenIdExist(){
        repository.deleteById(existId);
        Optional<Categoria> result = repository.findById(existId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnNoEmptyoptionalWhenIdExist(){
        Optional<Categoria> optional = repository.findById(existId);
        Assertions.assertTrue(optional.isPresent());

    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist(){
        Optional<Categoria> optional = repository.findById(nonExistId);

        Assertions.assertTrue(optional.isEmpty());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdExist(){
        categoria.setId(null);
        repository.save(categoria);

        Assertions.assertNotNull(categoria.getId());
        Assertions.assertEquals(countTotalCategorias+1, categoria.getId());
    }

    @Test
    public void createShouldReturnConstraintViolationWhenTituloIsNull(){
        categoria.setTitulo(null);

        // Validar o objeto usando o validator
        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);

        // Verificar se houve violações de restrição
        Assertions.assertFalse(violations.isEmpty());
        // Verificar se a mensagem de erro esperada foi retornada
        Assertions.assertEquals("Título não pode estar em branco", violations.iterator().next().getMessage());
    }
    @Test
    public void createShouldReturnConstraintViolationWhenCorIsNull(){
        categoria.setCor(null);
        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals("Cor não pode estar em branco", violations.iterator().next().getMessage());
    }

    @Test
    public void createShouldReturnConstrainViolationWhenCorPatternsIsInvalid(){
        categoria.setCor("#dd");
        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals("Entre com um padrão de cor Hexadecimal válido",violations.iterator().next().getMessage());

    }
}
