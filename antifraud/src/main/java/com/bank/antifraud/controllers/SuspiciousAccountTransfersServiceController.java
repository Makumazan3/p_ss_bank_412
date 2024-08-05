package com.bank.antifraud.controllers;


import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
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
@RequestMapping("/suspiciousAccountTransfers")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Validated
public class SuspiciousAccountTransfersServiceController {
    private final SuspiciousAccountTransfersService suspiciousAccountTransfersService;
    @GetMapping("/all")
    @Operation(summary = "Information about suspicious account transfers")
    @ApiResponse(responseCode = "200", description = "Suspicious account transfers got successfully",
            content = {@Content(schema = @Schema(implementation = SuspiciousAccountTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<SuspiciousAccountTransfersDto>> getAllSuspiciousAccountTransfers() {
        log.info("Request received to get all suspicious transfers by account number");
        List<SuspiciousAccountTransfersDto> transfers =
                suspiciousAccountTransfersService.getAllSuspiciousAccountTransfers();
        log.info("Returning {} suspicious transfers by account number.", transfers.size());

        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Information about suspicious account transfer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suspicious account transfer got successfully",
                    content = {@Content(schema = @Schema(implementation = SuspiciousAccountTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious account transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousAccountTransfersDto> getSuspiciousAccountTransfersById(
            @Valid @PathVariable("id") Long id) {
        log.info("Request received to get by id suspicious transfers by account number");
        SuspiciousAccountTransfersDto transfer = suspiciousAccountTransfersService.getSuspiciousAccountTransfersById(id);
        log.info("The SuspiciousAccountTransferDTO has gotten successfully with ID = {}", id);

        return ResponseEntity.ok(transfer);
    }

    @PostMapping ("/add")
    @Operation(summary = "Add new suspicious account transfer")
    @ApiResponse(responseCode = "201", description = "Entity \"SuspiciousAccountTransfer\" created successfully",
            content = {@Content(schema = @Schema(implementation = SuspiciousAccountTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<SuspiciousAccountTransfersDto> addSuspiciousAccountTransfers(
            @Valid @RequestBody SuspiciousAccountTransfersDto suspiciousAccountTransfersDto) {
        log.info("Request received to create a new entity: SuspiciousAccountTransfer");
        SuspiciousAccountTransfersDto createdSuspiciousAccountTransfersDto =
                suspiciousAccountTransfersService.addSuspiciousAccountTransfers(suspiciousAccountTransfersDto);
        log.info("Entity \"SuspiciousAccountTransfer\" created successfully with ID = {}",
                createdSuspiciousAccountTransfersDto.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuspiciousAccountTransfersDto);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update data suspicious account transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suspicious account transfer updated successfully",
                    content = {@Content(schema = @Schema(implementation = SuspiciousAccountTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious account transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousAccountTransfersDto> updateSuspiciousAccountTransfer(
            @PathVariable Long id,
            @Valid @RequestBody SuspiciousAccountTransfersDto suspiciousAccountTransfersDto) {

        log.info("Request received to update entity: SuspiciousAccountTransfers with ID = {}", id);

        try {
            // Обновление записи по идентификатору и данным
            SuspiciousAccountTransfersDto updatedTransfer =
                    suspiciousAccountTransfersService.updateSuspiciousAccountTransfers(id, suspiciousAccountTransfersDto);

            // Возвращаем обновленную запись
            log.info("Entity \"SuspiciousAccountTransfer\" updated successfully with ID = {}", updatedTransfer.getId());
            return ResponseEntity.ok(updatedTransfer);
        } catch (EntityNotFoundException e) {
            // Если запись не найдена
            log.warn("SuspiciousAccountTransfer with ID = {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Общая обработка ошибок
            log.error("Error updating SuspiciousAccountTransfer with ID = {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Delete suspicious account transfer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suspicious account transfer deleted successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious account transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<String> removeSuspiciousAccountTransfer(@Valid @PathVariable("id") Long id) {
        log.info("Request received to delete a new entity: SuspiciousAccountTransfer");
        suspiciousAccountTransfersService.deleteSuspiciousAccountTransfersById(id);
        log.info("Entity \"SuspiciousAccountTransfer\" deleted successfully with ID = {}", id);

        return ResponseEntity.ok(String.format("SuspiciousAccountTransfer with ID = %d was delete!", id));
    }

}

