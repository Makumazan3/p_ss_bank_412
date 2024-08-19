package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankDetailsController.class)
public class BankDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankDetailsService bankDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateBankDetails() throws Exception {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        Mockito.when(bankDetailsService.addBankDetails(any(BankDetailsDto.class))).thenReturn(bankDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/bankDetails/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankDetailsDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bankDetailsDto)));
    }

    @Test
    public void testShowAllBankDetails() throws Exception {
        List<BankDetailsDto> bankDetailsDtoList = Arrays.asList(new BankDetailsDto(), new BankDetailsDto());
        Mockito.when(bankDetailsService.getAllBankDetails()).thenReturn(bankDetailsDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bankDetails/showAllBankDetails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetOneBankDetails() throws Exception {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        Mockito.when(bankDetailsService.getBankDetailsById(anyLong())).thenReturn(bankDetailsDto);
        when(bankDetailsService.getBankDetailsById(anyLong())).thenReturn(bankDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/bankDetails/showOne/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bankDetailsDto)));
    }

    @Test
    public void testUpdateBankDetails() throws Exception {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/bankDetails/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankDetailsDto)))
                .andExpect(status().isOk());

        Mockito.verify(bankDetailsService).updateBankDetails(any(BankDetailsDto.class));
    }

    @Test
    public void testDeleteBankDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/bankDetails/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(bankDetailsService).deleteBankDetails(anyLong());
    }
}