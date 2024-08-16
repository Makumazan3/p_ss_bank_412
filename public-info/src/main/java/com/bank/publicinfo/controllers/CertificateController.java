package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
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
@RequestMapping("/certificate")
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping("/create")
    public ResponseEntity<CertificateDto> create(@RequestBody CertificateDto certificateDto) {
        final CertificateDto dto = certificateService.addCertificate(certificateDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("showAllCertificate")
    public ResponseEntity<List<CertificateDto>> showAll() {
        return new ResponseEntity<>(certificateService.getAllCertificate(), HttpStatus.OK);
    }

    @GetMapping("/showOne/{id}")
    public ResponseEntity<CertificateDto> getOne(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(certificateService.getCertificateById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CertificateDto> update(@RequestBody CertificateDto certificateDto) {
        certificateService.updateCertificate(certificateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id) {
        certificateService.deleteCertificate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}