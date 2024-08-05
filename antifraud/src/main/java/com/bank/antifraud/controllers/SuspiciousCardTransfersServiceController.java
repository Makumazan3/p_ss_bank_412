package com.bank.antifraud.controllers;


import com.bank.antifraud.dto.SuspiciousCardTransfersDto;
import com.bank.antifraud.service.SuspiciousCardTransfersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/suspiciousCardTransfers")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Validated
public class SuspiciousCardTransfersServiceController {
    private final SuspiciousCardTransfersService suspiciousCardTransfersService;
    @GetMapping("/all")
    @Operation(summary = "Information about suspicious card transfers")
    @ApiResponse(responseCode = "200", description = "Suspicious card transfers got successfully",
            content = {@Content(schema = @Schema(implementation = SuspiciousCardTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<SuspiciousCardTransfersDto>> getAllSuspiciousCardTransfers() {
        log.info("Request received to get all suspicious transfers by card number");
        List<SuspiciousCardTransfersDto> transfers =
                suspiciousCardTransfersService.getAllSuspiciousCardTransfers();
        log.info("Returning {} suspicious transfers by card number.", transfers.size());

        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Information about suspicious card transfer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suspicious card transfer got successfully",
                    content = {@Content(schema = @Schema(implementation = SuspiciousCardTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious card transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousCardTransfersDto> getSuspiciousCardTransfersById(
            @Valid @PathVariable("id") Long id) {
        log.info("Request received to get by id suspicious transfers by card number");
        SuspiciousCardTransfersDto transfer = suspiciousCardTransfersService.getSuspiciousCardTransfersById(id);
        log.info("The SuspiciousCardTransfersDto has gotten successfully with ID = {}", id);

        return ResponseEntity.ok(transfer);
    }

    @PostMapping ("/add")
    @Operation(summary = "Add new suspicious card transfer")
    @ApiResponse(responseCode = "201", description = "Entity \"SuspiciousCardTransfers\" created successfully",
            content = {@Content(schema = @Schema(implementation = SuspiciousCardTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<SuspiciousCardTransfersDto> addSuspiciousCardTransfers(
            @Valid @RequestBody SuspiciousCardTransfersDto suspiciousCardTransfersDto) {
        log.info("Request received to create a new entity: SuspiciousCardTransfers");
        SuspiciousCardTransfersDto createdSuspiciousCardTransfersDto =
                suspiciousCardTransfersService.addSuspiciousCardTransfers(suspiciousCardTransfersDto);
        log.info("Entity \"SuspiciousCardTransfers\" created successfully with ID = {}",
                createdSuspiciousCardTransfersDto.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuspiciousCardTransfersDto);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update data suspicious card transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suspicious card transfer updated successfully",
                    content = {@Content(schema = @Schema(implementation = SuspiciousCardTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious card transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousCardTransfersDto> updateSuspiciousCardTransfer(
            @PathVariable Long id,
            @Valid @RequestBody SuspiciousCardTransfersDto suspiciousCardTransfersDto) {

        log.info("Request received to update entity: SuspiciousCardTransfers with ID = {}", id);

        try {
            // Обновление записи по идентификатору и данным
            SuspiciousCardTransfersDto updatedTransfer =
                    suspiciousCardTransfersService.updateSuspiciousCardTransfers(id, suspiciousCardTransfersDto);

            // Возвращаем обновленную запись
            log.info("Entity \"SuspiciousCardTransfer\" updated successfully with ID = {}", updatedTransfer.getId());
            return ResponseEntity.ok(updatedTransfer);
        } catch (EntityNotFoundException e) {
            // Если запись не найдена
            log.warn("SuspiciousCardTransfer with ID = {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Общая обработка ошибок
            log.error("Error updating SuspiciousCardTransfer with ID = {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Delete suspicious card transfer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suspicious card transfer deleted successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious card transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<String> removeSuspiciousCardTransfer(@Valid @PathVariable("id") Long id) {
        log.info("Request received to delete a new entity: SuspiciousCardTransfer");
        suspiciousCardTransfersService.deleteSuspiciousCardTransfersById(id);
        log.info("Entity \"SuspiciousCardTransfer\" deleted successfully with ID = {}", id);

        return ResponseEntity.ok(String.format("SuspiciousCardTransfer with ID = %d was delete!", id));
    }

}

