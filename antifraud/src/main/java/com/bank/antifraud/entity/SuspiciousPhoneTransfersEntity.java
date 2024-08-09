package com.bank.antifraud.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "suspicious_phone_transfers", schema = "anti_fraud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuspiciousPhoneTransfersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column (name = "phone_transfer_id", nullable = false)
    Long phoneTransferId;

    @Column (name = "is_blocked", nullable = false)
    boolean isBlocked;

    @Column (name = "is_suspicious", nullable = false)
    boolean isSuspicious;

    @Column(name = "blocked_reason")
    String blockedReason;

    @Column(name = "suspicious_reason", nullable = false)
    String suspiciousReason;
}
