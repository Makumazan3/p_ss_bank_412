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
@RequestMapping("/suspiciousCardTransfers")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Validated
public class SuspiciousCardTransfersServiceController {
    private final SuspiciousCardTransfersService suspiciousCardTransfersService;
    @GetMapping("/all")
    @Operation(summary = "Информация о подозрительных переводах по картам")
    @ApiResponse(responseCode = "200", description = "Подозрительные переводы по картам успешно получены",
            content = {@Content(schema = @Schema(implementation = SuspiciousCardTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<SuspiciousCardTransfersDto>> getAllSuspiciousCardTransfers() {
        log.info("Получен запрос на получение всех подозрительных переводов по номеру карты");
        List<SuspiciousCardTransfersDto> transfers =
                suspiciousCardTransfersService.getAllSuspiciousCardTransfers();
        log.info("Возвращено {} подозрительных переводов по номеру карты", transfers.size());
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Информация о подозрительном переводе по карте по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подозрительный перевод по карте успешно получен",
                    content = {@Content(schema = @Schema(implementation = SuspiciousCardTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Подозрительный перевод по карте не найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousCardTransfersDto> getSuspiciousCardTransfersById(
            @Valid @PathVariable("id") Long id) {
        log.info("Получен запрос на получение подозрительных переводов по номеру карты по ID");
        SuspiciousCardTransfersDto transfer = suspiciousCardTransfersService.getSuspiciousCardTransfersById(id);
        log.info("TSuspiciousCardTransfersDto успешно получен с ID = {}", id);
        return ResponseEntity.ok(transfer);
    }

    @PostMapping ("/add")
    @Operation(summary = "Добавить новый подозрительный перевод по карте")
    @ApiResponse(responseCode = "201", description = "Объект \"SuspiciousCardTransfers\" успешно создан",
            content = {@Content(schema = @Schema(implementation = SuspiciousCardTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<SuspiciousCardTransfersDto> addSuspiciousCardTransfers(
            @Valid @RequestBody SuspiciousCardTransfersDto suspiciousCardTransfersDto) {
        log.info("Получен запрос на создание нового объекта: SuspiciousCardTransfers");
        SuspiciousCardTransfersDto createdSuspiciousCardTransfersDto =
                suspiciousCardTransfersService.addSuspiciousCardTransfers(suspiciousCardTransfersDto);
        log.info("Объект \"SuspiciousCardTransfers\" успешно создан с ID = {}",
                createdSuspiciousCardTransfersDto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuspiciousCardTransfersDto);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Обновить данные подозрительного перевода по карте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подозрительный перевод по карте успешно обновлен",
                    content = {@Content(schema = @Schema(implementation = SuspiciousCardTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Подозрительный перевод по карте не найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousCardTransfersDto> updateSuspiciousCardTransfer(
            @PathVariable Long id,
            @Valid @RequestBody SuspiciousCardTransfersDto suspiciousCardTransfersDto) {
        log.info("Получен запрос на обновление объекта: SuspiciousCardTransfers с ID = {}", id);
        try {
            SuspiciousCardTransfersDto updatedTransfer =
                    suspiciousCardTransfersService.updateSuspiciousCardTransfers(id, suspiciousCardTransfersDto);
            log.info("Объект \"SuspiciousCardTransfer\" успешно обновлен с ID = {}", updatedTransfer.getId());
            return ResponseEntity.ok(updatedTransfer);
        } catch (EntityNotFoundException e) {
            // Если запись не найдена
            log.warn("SuspiciousCardTransfer с ID = {} не найден", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Ошибка при обновлении SuspiciousCardTransfer с ID = {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить подозрительный перевод по карте по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подозрительный перевод по карте успешно удалён",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Подозрительный перевод по карте не найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<String> removeSuspiciousCardTransfer(@Valid @PathVariable("id") Long id) {
        log.info("Получен запрос на удаление нового объекта: SuspiciousCardTransfer");
        suspiciousCardTransfersService.deleteSuspiciousCardTransfersById(id);
        log.info("Объект \"SuspiciousCardTransfer\" успешно удалён с ID = {}", id);
        return ResponseEntity.ok(String.format("SuspiciousCardTransfer с ID = %d был удалён!", id));
    }
}

