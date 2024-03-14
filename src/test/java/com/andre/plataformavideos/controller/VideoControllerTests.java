package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VideoController.class)
public class VideoControllerTests {

    @MockBean
    VideoService service;

    @Autowired
    MockMvc mockMvc;

    private Long existId;

    @BeforeEach
    void setUp() {
        existId = 1L;

        Mockito.doNothing().when(service).deleteById(existId);
    }

    @Test
    @WithMockUser(username = "admin", roles = "{ADMIN}")
    public void  deleteShouldDeleteWhenIdExist() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/videos/{id}", existId)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNoContent());

    }
}
