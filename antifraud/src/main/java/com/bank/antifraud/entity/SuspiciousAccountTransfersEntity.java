package com.bank.antifraud.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "suspicious_account_transfers", schema = "anti_fraud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuspiciousAccountTransfersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "account_transfer_id", nullable = false)
    Long accountTransferId;

    @Column(name = "is_blocked", nullable = false)
    boolean isBlocked;

    @Column(name = "is_suspicious", nullable = false)
    boolean isSuspicious;

    @Column(name = "blocked_reason")
    String blockedReason;

    @Column(name = "suspicious_reason", nullable = false)
   String suspiciousReason;

}
