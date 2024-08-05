package com.bank.antifraud.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@FieldDefaults (level = AccessLevel.PRIVATE)
public class SuspiciousPhoneTransfersDto implements Serializable {
    Long id;
    Long phoneTransferId;
    boolean isBlocked;
    boolean isSuspicious;
    String blockedReason;
    String suspiciousReason;

}
