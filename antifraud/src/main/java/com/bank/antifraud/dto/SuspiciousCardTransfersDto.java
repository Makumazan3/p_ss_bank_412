package com.bank.antifraud.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Getter
@Setter
@FieldDefaults (level = AccessLevel.PRIVATE)
public class SuspiciousCardTransfersDto implements Serializable {
    Long id;
    Long cardTransferId;
    boolean isBlocked;
    boolean isSuspicious;
    String blockedReason;
    String suspiciousReason;

}
