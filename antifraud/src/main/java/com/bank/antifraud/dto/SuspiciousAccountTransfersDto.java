package com.bank.antifraud.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuspiciousAccountTransfersDto implements Serializable {

    Long id;
    Long accountTransferId;
    boolean isBlocked;
    boolean isSuspicious;
    String blockedReason;
    String suspiciousReason;

}
