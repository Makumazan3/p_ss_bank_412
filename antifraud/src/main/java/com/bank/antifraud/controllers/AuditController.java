package com.bank.antifraud.controllers;


import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/audit")
@AllArgsConstructor
@Slf4j
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/{id}")
    public AuditDto getById(@PathVariable long id) {
        log.info("Запуск сервисного метода getById");
        return auditService.getById(id);
    }

    @GetMapping("/all")
    public List<AuditDto> getAllAudit() {
        log.info("Запуск сервисного метода getAllAudit");
        return auditService.getAll();
    }

    @PostMapping("/add")
    @Operation(summary = "Add new suspicious audit")
    @ApiResponse(responseCode = "201", description = "Entity \"Audit\" created successfully",
            content = {@Content(schema = @Schema(implementation = AuditDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<AuditDto> addAudit(@Valid @RequestBody AuditDto auditDto) {
        log.info("Request received to create a new entity: AuditEntity");
        AuditDto createdAuditDto = auditService.createAudit(auditDto);
        log.info("Entity \"Audit\" created successfully with ID = {}", createdAuditDto.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuditDto);
    }
}

