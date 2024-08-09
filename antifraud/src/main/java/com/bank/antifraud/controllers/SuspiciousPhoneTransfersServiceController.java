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
@RequestMapping("/suspiciousPhoneTransfers")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Validated
public class SuspiciousPhoneTransfersServiceController {
    private final SuspiciousPhoneTransfersService suspiciousPhoneTransfersService;

    @GetMapping("/all")
    @Operation(summary = "Информация о подозрительных переводах по телефону")
    @ApiResponse(responseCode = "200", description = "Подозрительные переводы по телефону успешно получены",
            content = {@Content(schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<List<SuspiciousPhoneTransfersDto>> getAllSuspiciousPhoneTransfers() {
        log.info("Получен запрос на получение всех подозрительных переводов по номеру телефона");
        List<SuspiciousPhoneTransfersDto> transfers =
                suspiciousPhoneTransfersService.getAllSuspiciousPhoneTransfers();
        log.info("Возвращено {} подозрительных переводов по номеру телефона", transfers.size());
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Информация о подозрительном переводе по телефону по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подозрительный перевод по телефону успешно получен",
                    content = {@Content(schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Suspicious phone transfer not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousPhoneTransfersDto> getSuspiciousPhoneTransfersById(
            @Valid @PathVariable("id") Long id) {
        log.info("Получен запрос на получение подозрительных переводов по номеру телефона по ID");
        SuspiciousPhoneTransfersDto transfer = suspiciousPhoneTransfersService.getSuspiciousPhoneTransfersById(id);
        log.info("SuspiciousPhoneTransfersDto успешно получен с ID = {}", id);
        return ResponseEntity.ok(transfer);
    }

    @PostMapping ("/add")
    @Operation(summary = "Добавить новый подозрительный перевод по телефону")
    @ApiResponse(responseCode = "201", description = "Объект \"SuspiciousPhoneTransfers\" успешно создан",
            content = {@Content(schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    public ResponseEntity<SuspiciousPhoneTransfersDto> addSuspiciousPhoneTransfers(
            @Valid @RequestBody SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto) {
        log.info("Получен запрос на создание нового объекта: SuspiciousPhoneTransfers");
        SuspiciousPhoneTransfersDto createdSuspiciousPhoneTransfersDto =
                suspiciousPhoneTransfersService.addSuspiciousPhoneTransfers(suspiciousPhoneTransfersDto);
        log.info("Объект \"SuspiciousPhoneTransfers\" успешно создан с ID = {}",
                createdSuspiciousPhoneTransfersDto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuspiciousPhoneTransfersDto);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Обновить данные подозрительного перевода по телефону")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подозрительный перевод по телефону успешно обновлен",
                    content = {@Content(schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Подозрительный перевод по телефону не найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<SuspiciousPhoneTransfersDto> updateSuspiciousPhoneTransfer(
            @PathVariable Long id,
            @Valid @RequestBody SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto) {
        log.info("Получен запрос на обновление объекта: SuspiciousPhoneTransfers с ID = {}", id);
        try {
            SuspiciousPhoneTransfersDto updatedTransfer =
                    suspiciousPhoneTransfersService.updateSuspiciousPhoneTransfers(id, suspiciousPhoneTransfersDto);
            log.info("Объект \"SuspiciousPhoneTransfers\" успешно обновлен с ID = {}", updatedTransfer.getId());
            return ResponseEntity.ok(updatedTransfer);
        } catch (EntityNotFoundException e) {
            log.warn("SuspiciousPhoneTransfer с ID = {} не найден", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Ошибка при обновлении SuspiciousPhoneTransfer с ID = {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить подозрительный перевод по телефону по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подозрительный перевод по телефону успешно удалён",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Подозрительный перевод по телефону не найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<String> removeSuspiciousPhoneTransfer(@Valid @PathVariable("id") Long id) {
        log.info("Получен запрос на удаление объекта: SuspiciousPhoneTransfers");
        suspiciousPhoneTransfersService.deleteSuspiciousPhoneTransfersById(id);
        log.info("Объект \"SuspiciousPhoneTransfers\" успешно удалён с ID = {}", id);
        return ResponseEntity.ok(String.format("SuspiciousPhoneTransfer с ID = %d был удален!", id));
    }
}

