package com.bank.transfer.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Класс-DTO, представляющий информацию о Audit.
 * Используется для передачи данных между модулями либо клиенту.
 **/

@Data
public class AuditDto {
    //Первичный ключ
    @NotBlank
    private long id;

    //Вид трансфера
    @Size(max = 40, message = "Максимальная длинна entityType 40 символов")
    @NotBlank
    private String entityType;

    //Вид операции
    @Size(max = 255, message = "Максимальная длинна operationType 255 символов")
    @NotBlank
    private String operationType;

    //Создатель операции
    @Size(max = 255, message = "Максимальная длинна createdBy 255 символов")
    @NotBlank
    private String createdBy;

    //Кем операция изменена
    @Size(max = 255, message = "Максимальная длинна modifiedBy 255 символов")
    private String modifiedBy;

    //Дата создания операции
    @NotBlank
    private LocalDateTime createdAt;

    //Дата изменения операции
    private LocalDateTime modifiedAt;

    //Операция в виде JSON при создании
    private String newEntityJSON;

    //Операция в виде JSON после изменения
    @NotBlank
    private String entityJSON;

}
