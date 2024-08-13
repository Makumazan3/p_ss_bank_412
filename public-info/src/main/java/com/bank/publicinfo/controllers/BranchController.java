package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/branch")
public class BranchController {

    private final BranchService branchService;

    /**
     * Метод, отвечающий за создание новой сущности
     */
    @PostMapping(value = "/create")
    public ResponseEntity<BranchDto> create(@RequestBody BranchDto branchDto) {
        BranchDto dto = branchService.addBranch(branchDto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Метод, отвечающий за отображение всех сущностей
     */

    @GetMapping("/showAllBranch")
    public ResponseEntity<List<BranchDto>> showAll() {
        return new ResponseEntity<>(branchService.getAllBranch(), HttpStatus.OK);
    }

    /**
     * Метод, отвечающий за отображение одной сущности
     */

    @GetMapping("/showOne/{id}")
    public ResponseEntity<BranchDto> getOne(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(branchService.getBranchById(id), HttpStatus.OK);
    }

    /**
     * Метод, отвечающий за обновление имеющейся сущности
     */
    @PutMapping("/update")
    public ResponseEntity<BranchDto> update(@RequestBody BranchDto branchDto) {
        branchService.updateBranch(branchDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Метод, отвечающий за удаление одной сущности
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BranchDto> delete(@PathVariable(name = "id") Long id) {
        branchService.deleteBranch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
