package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.service.AtmService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AtmController.class)
class AtmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtmService atmService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        AtmDto atmDto = new AtmDto();
        Mockito.when(atmService.addAtm(any(AtmDto.class))).thenReturn(atmDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/atm/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atmDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(atmDto)));
    }

    @Test
    void showAll() throws Exception {
        List<AtmDto> atmList = Arrays.asList(new AtmDto(), new AtmDto());
        Mockito.when(atmService.getAllAtm()).thenReturn(atmList);

        mockMvc.perform(MockMvcRequestBuilders.get("/atm/showAllAtm")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getOne() throws Exception {
        AtmDto atmDto = new AtmDto();
        Mockito.when(atmService.getAtmById(anyLong())).thenReturn(atmDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/atm/showOne/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(atmDto.getId())));
    }

    @Test
    void update() throws Exception {
        AtmDto atmDto = new AtmDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/atm/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atmDto)))
                .andExpect(status().isOk());

        Mockito.verify(atmService).updateAtm(any(AtmDto.class));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/atm/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(atmService).deleteAtm(anyLong());
    }
}