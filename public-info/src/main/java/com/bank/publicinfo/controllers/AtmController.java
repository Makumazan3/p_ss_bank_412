package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.service.AtmService;
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
@RequestMapping("/atm")
public class AtmController {

    private final AtmService atmService;

    @PostMapping(value = "/create")
    public ResponseEntity<AtmDto> create(@RequestBody AtmDto atmDto) {
        final AtmDto dto = atmService.addAtm(atmDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/showAllAtm")
    public ResponseEntity<List<AtmDto>> showAll() {
        return new ResponseEntity<>(atmService.getAllAtm(), HttpStatus.OK);
    }

    @GetMapping(value = "/showOne/{id}")
    public ResponseEntity<AtmDto> getOne(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(atmService.getAtmById(id), HttpStatus.OK);
    }

    @PutMapping(value = "update")
    public ResponseEntity<AtmDto> update(@RequestBody AtmDto atmDto) {
        atmService.updateAtm(atmDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id) {
        atmService.deleteAtm(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
