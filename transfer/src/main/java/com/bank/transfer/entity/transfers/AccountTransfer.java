package com.bank.transfer.entity.transfers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

/**
 * Класс-сущность "AccountTransfer".
 * Необходим для хранения информации о AccountTransfer в базе данных.
 **/

@Entity
@Table(schema = "transfer", name = "account_transfer")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountTransfer {
    //Первичный ключ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    //Номер учетной записи
    @Column(name = "account_number", nullable = false, unique = true)
    @Min(value = 1L, message = "accountNumber не может быть равен нулю")
    private long accountNumber;

    //Сумма перевода
    @Column(name = "amount", nullable = false, precision = 20, scale = 2)
    @Min(value = 1L, message = "amountAccount должен быть больше нуля")
    private BigDecimal amount;

    //Цель перевода
    @Column(name = "purpose")
    private String purpose;

    //Идентификатор учетной записи
    @Column(name = "account_details_id", nullable = false)
    @Min(value = 1L, message = "accountDetailsIdCard не может быть равен нулю")
    private long accountDetailsId;


}
