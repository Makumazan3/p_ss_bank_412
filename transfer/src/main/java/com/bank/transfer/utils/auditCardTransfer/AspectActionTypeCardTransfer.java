package com.bank.transfer.utils.auditCardTransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс-enum, содержащий в себе константы,
 * необходимые для различия типов действий,
 * которые могут быть выполнены в рамках
 * аудита @AuditableCardTransfer.
 **/

@Getter
@AllArgsConstructor
public enum AspectActionTypeCardTransfer {

    CREATE_CARD("CREATE CARD"),
    UPDATE_CARD("UPDATE CARD"),
    DELETE_CARD("DELETE CARD");

    private final String description;

}
