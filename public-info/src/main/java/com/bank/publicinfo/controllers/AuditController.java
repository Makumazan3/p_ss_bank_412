package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;

    @PostMapping("/create")
    @Operation(summary = "Create new audit.")
    @ApiResponse(responseCode = "201"
            , description = "The entity was created successfully!"
            , content = {@Content(schema = @Schema(implementation = AuditDto.class)
            , mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<AuditDto> create(@Valid @RequestBody AuditDto auditDto) {
        log.info("New entity Audit is request to create.");
        AuditDto dto = auditService.addAudit(auditDto);
        log.info("The entity Audit was created successfully with ID = {}", dto.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/showAllAudit")
    @Operation(summary = "Show all audits.")
    @ApiResponse(responseCode = "200"
            , description = "All audits get successfully!"
            , content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<AuditDto>> showAll() {
        log.info("This method is started to show all entities.");
        return new ResponseEntity<>(auditService.getAllAudit(), HttpStatus.OK);
    }

    @GetMapping("/showOne/{id}")
    @Operation(summary = "Get one audit by ID.")
    @ApiResponse(responseCode = "200"
            , description = "Audit is got successfully."
            , content = {@Content(schema = @Schema(implementation = AuditDto.class)
            , mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<AuditDto> getOne(@PathVariable(name = "id") Long id) {
        log.info("This method is started to show one entity by ID = {}.", id);
        return new ResponseEntity<>(auditService.getAuditById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update audit.")
    @ApiResponse(responseCode = "200"
            , description = "Audit update successfully."
            , content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<HttpStatus> update(@Valid @RequestBody AuditDto auditDto) {
        log.info("Audit will be update");
        auditService.updateAudit(auditDto);
        log.info("Audit updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete audit by ID.")
    @ApiResponse(responseCode = "204"
            , description = "Audit delete successfully!")
    public ResponseEntity<AuditDto> delete(@PathVariable(name = "id") Long id) {
        log.info("Request to delete audit with ID = {}", id);
        auditService.deleteAudit(id);
        log.info("Audit by ID = {} was deleted successfully!", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
