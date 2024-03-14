package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.dto.CategoriaDto;
import com.andre.plataformavideos.exceptions.ResourceNotFoundException;
import com.andre.plataformavideos.service.CategoriaService;
import com.andre.plataformavideos.tests.factory.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTests {

    @MockBean
    private CategoriaService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existId;
    private Long nonExistId;
    private Long indentedId;
    private CategoriaDto categoriaDto;

    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExistId = 2L;
        indentedId = 3L;

        categoriaDto = Factory.createCategoriaDTO();

        Mockito.when(service.findAllCategorias()).thenReturn(List.of(categoriaDto));
        Mockito.when(service.findById(existId)).thenReturn(categoriaDto);
        Mockito.when(service.findById(nonExistId)).thenThrow(ResourceNotFoundException.class);
        //Mockito.when(service.updateCategoria(existId, categoriaDto)).thenReturn(categoriaDto);
        //Mockito.when(service.createCategoria(categoriaDto)).thenReturn(categoriaDto);

        Mockito.doNothing().when(service).deleteById(existId);


    }
    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void deleteShouldDeleteWhenIdExist() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/categorias/{id}/",existId)
                .accept(APPLICATION_JSON));

        result.andExpect(status().isNoContent());

    }


    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void createShouldReturnCategoriaDto() throws Exception {
        // TODO - ERRO 403 FORBIDDEN
        String jsonBody = objectMapper.writeValueAsString(categoriaDto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/categorias")
                        .content(jsonBody)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON));
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
    }


    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void updateShouldReturnCategoriaDtoWhenIdExist() throws Exception {
        // TODO - ERRO 403 FORBIDDEN
        String jsonBody = objectMapper.writeValueAsString(categoriaDto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/categorias/{id}", existId)
                .content(jsonBody)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.titulo").exists());
        result.andExpect(jsonPath("$.cor").exists());
        result.andExpect(jsonPath("$.id").value(categoriaDto.getId()));
    }


    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void findAllShouldReturnCategoriaDtoList() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categorias/")
                .accept(APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void findByIdShouldReturnCategoriaDtoWhenIdExist() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categorias/{id}/", existId)
                .accept(APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(categoriaDto.getId()));
        result.andExpect(jsonPath("$.titulo").value(categoriaDto.getTitulo()));

    }

    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExits() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categorias/{id}/", nonExistId));
        result.andExpect(status().isNotFound());
    }
}
