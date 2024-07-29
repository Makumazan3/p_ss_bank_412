package com.bank.transfer.utils.auditPhoneTransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс-enum, содержащий в себе константы,
 * необходимые для различия типов действий,
 * которые могут быть выполнены в рамках
 * аудита @AuditablePhoneTransfer.
 **/

@Getter
@AllArgsConstructor
public enum AspectActionTypePhoneTransfer {

    CREATE_PHONE("CREATE PHONE"),
    UPDATE_PHONE("UPDATE PHONE"),
    DELETE_PHONE("DELETE PHONE");

    private final String description;

}
