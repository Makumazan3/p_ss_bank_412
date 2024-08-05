package com.bank.history.controller;


import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.service.HistoryService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping()
public class HistoryController {

    private final HistoryService service;

    @PostMapping("/create")
    public ResponseEntity<HistoryEntity> create(@RequestBody HistoryDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<HistoryEntity> readById(Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HistoryEntity>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HistoryEntity> update(@RequestParam Long id, @RequestBody HistoryDto dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        service.deleteById(id);
        return HttpStatus.OK;
    }

}
