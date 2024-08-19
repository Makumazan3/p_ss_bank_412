package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
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

@WebMvcTest(BranchController.class)
class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchService branchService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        BranchDto branchDto = new BranchDto();
        Mockito.when(branchService.addBranch(any(BranchDto.class))).thenReturn(branchDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/branch/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(branchDto)));
    }

    @Test
    void showAll() throws Exception {
        List<BranchDto> branchDtoList = Arrays.asList(new BranchDto(), new BranchDto());
        Mockito.when(branchService.getAllBranch()).thenReturn(branchDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/branch/showAllBranch")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getOne() throws Exception {
        BranchDto branchDto = new BranchDto();
        Mockito.when(branchService.getBranchById(anyLong())).thenReturn(branchDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/branch/showOne/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(branchDto.getId())));
    }

    @Test
    void update() throws Exception {
        BranchDto branchDto = new BranchDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/branch/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchDto)))
                .andExpect(status().isOk());

        Mockito.verify(branchService).updateBranch(any(BranchDto.class));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/branch/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(branchService).deleteBranch(anyLong());
    }
}