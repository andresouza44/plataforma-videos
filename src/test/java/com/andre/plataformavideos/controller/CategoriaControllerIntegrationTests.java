package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.dto.CategoriaDto;
import com.andre.plataformavideos.exceptions.ResourceNotFoundException;
import com.andre.plataformavideos.service.CategoriaService;
import com.andre.plataformavideos.tests.factory.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoriaControllerIntegrationTests {


    @Autowired
    private CategoriaService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private CategoriaDto categoriaDto;

    private Long existId;
    private Long nonExistId;
    private Long indentedId;

    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExistId = 1000L;
        indentedId = 2L;

        categoriaDto = Factory.createCategoriaDTO();
    }


    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void findAllShouldReturnCategoriaDtoList() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categorias/")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[0].id").exists());
        result.andExpect(jsonPath("$[0].titulo").exists());
        result.andExpect(jsonPath("$[0].cor").exists());
        result.andExpect(jsonPath("$[0].titulo").value("Educativos"));
        result.andExpect(jsonPath("$[1].titulo").value("VideoClipe"));

    }
    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void createShouldReturnCategoriaDto() throws Exception {
        CategoriaDto dto = new CategoriaDto(null,"TESTE","#00FFFF");

        String jsonBody = objectMapper.writeValueAsString(dto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.titulo").exists());
        result.andExpect(jsonPath("$.cor").exists());
        result.andExpect(jsonPath("$.id").value(4));
        result.andExpect(jsonPath("$.titulo").value("TESTE"));
        result.andExpect(jsonPath("$.cor").value("#00FFFF"));
    }

    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void findByIdShouldReturnCategoriaDtoWhenIdExist() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categorias/{id}/", existId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.titulo").exists());
        result.andExpect(jsonPath("$.cor").exists());
        result.andExpect(jsonPath("$.id").value(1));
        result.andExpect(jsonPath("$.titulo").value("Educativos"));
        result.andExpect(jsonPath("$.cor").value("#00FFFF"));

    }
    @Test
    @WithMockUser(username = "admin",roles = {"ADMIN"})
    public void findByIdshouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception {
            ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categorias/{id}/", nonExistId)
                    .accept(MediaType.APPLICATION_JSON));
            result.andExpect(status().isNotFound());

        }

}
