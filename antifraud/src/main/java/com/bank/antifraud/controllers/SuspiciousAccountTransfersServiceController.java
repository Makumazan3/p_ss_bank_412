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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @Operation(summary = "Информация о подозрительных переводах по счёту")
    @ApiResponse(responseCode = "200", description = "Suspicious account transfers got successfully",
            content = {@Content(schema = @Schema(implementation = SuspiciousAccountTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<SuspiciousAccountTransfersDto>> getAllSuspiciousAccountTransfers() {
        log.info("Получен запрос на получение всех подозрительных переводов по номеру счета");
        List<SuspiciousAccountTransfersDto> transfers =
                suspiciousAccountTransfersService.getAllSuspiciousAccountTransfers();
        log.info("Возвращено {} подозрительных переводов по номеру счета", transfers.size());
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Информация о подозрительном переводе по счёту по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подозрительный перевод по счёту успешно получен",
                    content = {@Content(schema = @Schema(implementation = SuspiciousAccountTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Подозрительный перевод по счету не найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousAccountTransfersDto> getSuspiciousAccountTransfersById(
            @Valid @PathVariable("id") Long id) {
        log.info("Получен запрос на получение подозрительных переводов по номеру счета по ID");
        SuspiciousAccountTransfersDto transfer = suspiciousAccountTransfersService.getSuspiciousAccountTransfersById(id);
        log.info("SuspiciousAccountTransferDTO успешно получен с ID = {} ", id);
        return ResponseEntity.ok(transfer);
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить новый подозрительный перевод по счёту")
    @ApiResponse(responseCode = "201", description = "Объект \"SuspiciousAccountTransfer\" успешно создан",
            content = {@Content(schema = @Schema(implementation = SuspiciousAccountTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<SuspiciousAccountTransfersDto> addSuspiciousAccountTransfers(
            @Valid @RequestBody SuspiciousAccountTransfersDto suspiciousAccountTransfersDto) {
        log.info("Получен запрос на создание нового объекта: SuspiciousAccountTransfer");
        SuspiciousAccountTransfersDto createdSuspiciousAccountTransfersDto =
                suspiciousAccountTransfersService.addSuspiciousAccountTransfers(suspiciousAccountTransfersDto);
        log.info("Объект \"SuspiciousAccountTransfer\" успешно создан с ID = {}",
                createdSuspiciousAccountTransfersDto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuspiciousAccountTransfersDto);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Обновить данные подозрительного перевода по счёту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подозрительный перевод по счёту успешно обновлен",
                    content = {@Content(schema = @Schema(implementation = SuspiciousAccountTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Подозрительный перевод по счёту не найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousAccountTransfersDto> updateSuspiciousAccountTransfer(
            @PathVariable Long id,
            @Valid @RequestBody SuspiciousAccountTransfersDto suspiciousAccountTransfersDto) {

        log.info("Получен запрос на обновление объекта: SuspiciousAccountTransfers с ID = {}", id);

        try {
            SuspiciousAccountTransfersDto updatedTransfer =
                    suspiciousAccountTransfersService.updateSuspiciousAccountTransfers(id, suspiciousAccountTransfersDto);
            log.info("Объект \"SuspiciousAccountTransfer\" успешно обновлен с ID = {}", updatedTransfer.getId());
            return ResponseEntity.ok(updatedTransfer);
        } catch (EntityNotFoundException e) {
            log.warn("SuspiciousAccountTransfer с ID = {} не найден", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Ошибка при обновлении SuspiciousAccountTransfer с ID = {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить подозрительный перевод по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подозрительный перевод по счёту успешно удалён",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Подозрительный перевод по счёту не найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })

    public ResponseEntity<String> removeSuspiciousAccountTransfer(@Valid @PathVariable("id") Long id) {
        log.info("Получен запрос на удаление объекта: SuspiciousAccountTransfer");
        suspiciousAccountTransfersService.deleteSuspiciousAccountTransfersById(id);
        log.info("Объект \"SuspiciousAccountTransfer\" успешно удалён с ID = {}", id);
        return ResponseEntity.ok(String.format("SuspiciousAccountTransfer с ID = %d был удален!", id));
    }
}

