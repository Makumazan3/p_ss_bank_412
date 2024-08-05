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
 * Класс-сущность сущность "PhoneTransfer".
 * Необходим для хранения информации о PhoneTransfer в базе данных.
 **/

@Entity
@Table(schema = "transfer", name = "phone_transfer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneTransfer {
    //Первичный ключ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    //Номер телефона
    @Column(name = "phone_number", nullable = false, unique = true)
    @Min(value = 1, message = "phoneNumber не может быть равен нулю")
    private long phoneNumber;

    //Сумма перевода
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    @Min(value = 1, message = "amountPhone должен быть больше нуля")
    private BigDecimal amount;

    //Цель перевода
    @Column(name = "purpose")
    private String purpose;

    //Идентификатор учетной записи
    @Column(name = "account_details_id", nullable = false)
    @Min(value = 1, message = "accountDetailsIdPhone не может быть равен нулю")
    private long accountDetailsId;

}
