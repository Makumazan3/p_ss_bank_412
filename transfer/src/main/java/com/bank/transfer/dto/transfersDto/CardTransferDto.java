package com.bank.transfer.dto.transfersDto;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс-DTO, представляющий/получающий информацию о CardTransfer.
 * Используется для передачи/получения данных между модулями либо клиенту.
 **/

@Data
public class CardTransferDto {
    //Первичный ключ
    @NotBlank
    private long id;

    //Номер карты
    @NotBlank
    @Min(value = 1, message = "cardNumber не может быть равен нулю")
    private long cardNumber;

    //Сумма перевода
    @NotBlank
    @Min(value = 1, message = "amountCard должен быть больше нуля")
    private BigDecimal amount;

    //Цель перевода
    private String purpose;

    //Идентификатор учетной записи
    @NotBlank
    @Min(value = 1, message = "accountDetailsIdCard не может быть равен нулю")
    private long accountDetailsId;

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }
}
