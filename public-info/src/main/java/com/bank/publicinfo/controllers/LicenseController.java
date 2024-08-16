package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/license")
public class LicenseController {

    private final LicenseService licenseService;

    @PostMapping(value = "/create")
    public ResponseEntity<LicenseDto> create(@RequestBody LicenseDto licenseDto) {
        final LicenseDto dto = licenseService.addLicense(licenseDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "showAllLicense")
    public ResponseEntity<List<LicenseDto>> showAll() {
        return new ResponseEntity<>(licenseService.getAllLicense(), HttpStatus.OK);
    }

    @GetMapping(value = "/showOne/{id}")
    public ResponseEntity<LicenseDto> getOne(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(licenseService.getLicenseById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<LicenseDto> update(@RequestBody LicenseDto licenseDto) {
        licenseService.updateLicense(licenseDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id) {
        licenseService.deleteLicense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
