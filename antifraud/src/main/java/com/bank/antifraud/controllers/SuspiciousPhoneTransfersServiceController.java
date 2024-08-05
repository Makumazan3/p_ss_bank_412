package com.bank.antifraud.controllers;



import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
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
@RequestMapping("/suspiciousPhoneTransfers")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Validated
public class SuspiciousPhoneTransfersServiceController {
    private final SuspiciousPhoneTransfersService suspiciousPhoneTransfersService;

    @GetMapping("/all")
    @Operation(summary = "Information about suspicious phone transfers")
    @ApiResponse(responseCode = "200", description = "Suspicious phone transfers got successfully",
            content = {@Content(schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<SuspiciousPhoneTransfersDto>> getAllSuspiciousPhoneTransfers() {
        log.info("Request received to get all suspicious transfers by phone number");
        List<SuspiciousPhoneTransfersDto> transfers =
                suspiciousPhoneTransfersService.getAllSuspiciousPhoneTransfers();
        log.info("Returning {} suspicious transfers by phone number.", transfers.size());

        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Information about suspicious phone transfer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suspicious phone transfer got successfully",
                    content = {@Content(schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious phone transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousPhoneTransfersDto> getSuspiciousPhoneTransfersById(
            @Valid @PathVariable("id") Long id) {
        log.info("Request received to get by id suspicious transfers by phone number");
        SuspiciousPhoneTransfersDto transfer = suspiciousPhoneTransfersService.getSuspiciousPhoneTransfersById(id);
        log.info("The SuspiciousPhoneTransfersDto has gotten successfully with ID = {}", id);

        return ResponseEntity.ok(transfer);
    }

    @PostMapping ("/add")
    @Operation(summary = "Add new suspicious phone transfer")
    @ApiResponse(responseCode = "201", description = "Entity \"SuspiciousPhoneTransfers\" created successfully",
            content = {@Content(schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<SuspiciousPhoneTransfersDto> addSuspiciousPhoneTransfers(
            @Valid @RequestBody SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto) {
        log.info("Request received to create a new entity: SuspiciousPhoneTransfers");
        SuspiciousPhoneTransfersDto createdSuspiciousPhoneTransfersDto =
                suspiciousPhoneTransfersService.addSuspiciousPhoneTransfers(suspiciousPhoneTransfersDto);
        log.info("Entity \"SuspiciousPhoneTransfers\" created successfully with ID = {}",
                createdSuspiciousPhoneTransfersDto.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuspiciousPhoneTransfersDto);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update data suspicious phone transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suspicious phone transfer updated successfully",
                    content = {@Content(schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious phone transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousPhoneTransfersDto> updateSuspiciousPhoneTransfer(
            @PathVariable Long id,
            @Valid @RequestBody SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto) {

        log.info("Request received to update entity: SuspiciousPhoneTransfers with ID = {}", id);

        try {
            // Обновление записи по идентификатору и данным
            SuspiciousPhoneTransfersDto updatedTransfer =
                    suspiciousPhoneTransfersService.updateSuspiciousPhoneTransfers(id, suspiciousPhoneTransfersDto);

            // Возвращаем обновленную запись
            log.info("Entity \"SuspiciousPhoneTransfers\" updated successfully with ID = {}", updatedTransfer.getId());
            return ResponseEntity.ok(updatedTransfer);
        } catch (EntityNotFoundException e) {
            // Если запись не найдена
            log.warn("SuspiciousPhoneTransfer with ID = {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Общая обработка ошибок
            log.error("Error updating SuspiciousPhoneTransfer with ID = {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Delete suspicious phone transfer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suspicious phone transfer deleted successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious phone transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<String> removeSuspiciousPhoneTransfer(@Valid @PathVariable("id") Long id) {
        log.info("Request received to delete a new entity: SuspiciousPhoneTransfers");
        suspiciousPhoneTransfersService.deleteSuspiciousPhoneTransfersById(id);
        log.info("Entity \"SuspiciousPhoneTransfers\" deleted successfully with ID = {}", id);

        return ResponseEntity.ok(String.format("SuspiciousPhoneTransfer with ID = %d was delete!", id));
    }

}

