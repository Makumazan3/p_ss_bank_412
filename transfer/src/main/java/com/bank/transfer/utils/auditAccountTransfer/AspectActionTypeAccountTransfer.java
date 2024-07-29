package com.bank.transfer.utils.auditAccountTransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс-enum, содержащий в себе константы,
 * необходимые для различия типов действий,
 * которые могут быть выполнены в рамках
 * аудита @AuditableAccountTransfer.
 **/

@Getter
@AllArgsConstructor
public enum AspectActionTypeAccountTransfer {

    CREATE_ACCOUNT("CREATE ACCOUNT"),
    UPDATE_ACCOUNT("UPDATE ACCOUNT"),
    DELETE_ACCOUNT("DELETE ACCOUNT");

    private final String description;

}
