package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
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

@WebMvcTest(CertificateController.class)
class CertificateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificateService certificateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        CertificateDto certificateDto = new CertificateDto();
        Mockito.when(certificateService.addCertificate(any(CertificateDto.class))).thenReturn(certificateDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/certificate/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(certificateDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(certificateDto)));
    }

    @Test
    void showAll() throws Exception {
        List<CertificateDto> certificateList = Arrays.asList(new CertificateDto(), new CertificateDto());
        Mockito.when(certificateService.getAllCertificate()).thenReturn(certificateList);

        mockMvc.perform(MockMvcRequestBuilders.get("/certificate/showAllCertificate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getOne() throws Exception {
        CertificateDto certificateDto = new CertificateDto();
        Mockito.when(certificateService.getCertificateById(anyLong())).thenReturn(certificateDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/certificate/showOne/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(certificateDto.getId())));
    }

    @Test
    void update() throws Exception {
        CertificateDto certificateDto = new CertificateDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/certificate/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(certificateDto)))
                .andExpect(status().isOk());

        Mockito.verify(certificateService).updateCertificate(any(CertificateDto.class));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/certificate/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(certificateService).deleteCertificate(anyLong());
    }
}