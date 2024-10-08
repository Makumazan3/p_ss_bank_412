package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping(value = "/bankDetails")
public class BankDetailsController {

    private final BankDetailsService bankDetailsService;

    @Timed(value = "controller getCreate")
    @PostMapping(value = "/create")
    public ResponseEntity<BankDetailsDto> create(@RequestBody BankDetailsDto bankDetailsDto) {
        final BankDetailsDto dto = bankDetailsService.addBankDetails(bankDetailsDto);
        return ResponseEntity.ok(dto);
    }

    @Timed(value = "controller getReadAll")
    @GetMapping(value = "/showAllBankDetails")
    public ResponseEntity<List<BankDetailsDto>> showAll() {
        return new ResponseEntity<>(bankDetailsService.getAllBankDetails(), HttpStatus.OK);
    }

    @Timed(value = "controller getReadOneById")
    @GetMapping(value = "/showOne/{id}")
    public ResponseEntity<BankDetailsDto> getOne(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(bankDetailsService.getBankDetailsById(id), HttpStatus.OK);
    }

    @Timed(value = "controller Update")
    @PutMapping(value = "/update")
    public ResponseEntity<BankDetailsDto> update(@RequestBody BankDetailsDto bankDetailsDto) {
        bankDetailsService.updateBankDetails(bankDetailsDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Timed(value = "controller delete")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BankDetailsDto> delete(@PathVariable(name = "id") Long id) {
        bankDetailsService.deleteBankDetails(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}