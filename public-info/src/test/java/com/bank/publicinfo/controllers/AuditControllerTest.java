package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.service.AuditService;
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

@WebMvcTest(AuditController.class)
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditService auditService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        AuditDto auditDto = new AuditDto();
        Mockito.when(auditService.addAudit(any(AuditDto.class))).thenReturn(auditDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/audit/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(auditDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(auditDto)));
    }

    @Test
    void showAll() throws Exception {
        List<AuditDto> auditList = Arrays.asList(new AuditDto(), new AuditDto());
        Mockito.when(auditService.getAllAudit()).thenReturn(auditList);

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/showAllAudit")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getOne() throws Exception {
        AuditDto auditDto = new AuditDto();
        Mockito.when(auditService.getAuditById(anyLong())).thenReturn(auditDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/showOne/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(auditDto.getId())));
    }

    @Test
    void update() throws Exception {
        AuditDto auditDto = new AuditDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/audit/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(auditDto)))
                .andExpect(status().isOk());

        Mockito.verify(auditService).updateAudit(any(AuditDto.class));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/audit/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(auditService).deleteAudit(anyLong());
    }
}