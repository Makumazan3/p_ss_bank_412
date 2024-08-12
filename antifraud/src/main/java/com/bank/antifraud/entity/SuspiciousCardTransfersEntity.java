package com.bank.antifraud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "suspicious_card_transfers", schema = "anti_fraud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuspiciousCardTransfersEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Column (name = "card_transfer_id", nullable = false)
    private long cardTransferId;

   @Column (name = "is_blocked", nullable = false)
    private boolean isBlocked;

   @Column (name = "is_suspicious", nullable = false)
    private boolean isSuspicious;

    @Column(name = "blocked_reason")
    private String blockedReason;

    @Column(name = "suspicious_reason", nullable = false)
    private String suspiciousReason;
}
