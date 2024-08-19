package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LicenseController.class)
class LicenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseService licenseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        LicenseDto licenseDto = new LicenseDto();
        Mockito.when(licenseService.addLicense(any(LicenseDto.class))).thenReturn(licenseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/license/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(licenseDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(licenseDto)));
    }

    @Test
    void showAll() throws Exception {
        List<LicenseDto> licenseList = Arrays.asList(new LicenseDto(), new LicenseDto());
        Mockito.when(licenseService.getAllLicense()).thenReturn(licenseList);

        mockMvc.perform(MockMvcRequestBuilders.get("/license/showAllLicense")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getOne() throws Exception {
        LicenseDto licenseDto = new LicenseDto();
        Mockito.when(licenseService.getLicenseById(anyLong())).thenReturn(licenseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/license/showOne/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(licenseDto.getId())));
    }

    @Test
    void update() throws Exception {
        LicenseDto licenseDto = new LicenseDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/license/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(licenseDto)))
                .andExpect(status().isOk());

        Mockito.verify(licenseService).updateLicense(any(LicenseDto.class));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/license/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(licenseService).deleteLicense(anyLong());
    }
}