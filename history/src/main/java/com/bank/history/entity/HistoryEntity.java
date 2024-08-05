package com.bank.history.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "history", schema = "history")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "transfer_audit_id", nullable = false)
    Long transferAuditId;

    @Column(name = "profile_audit_id", nullable = false)
    Long profileAuditId;

    @Column(name = "account_audit_id", nullable = false)
    Long accountAuditId;

    @Column(name = "anti_fraud_audit_id", nullable = false)
    Long antiFraudAuditId;

    @Column(name = "public_bank_info_audit_id", nullable = false)
    Long publicBankInfoAuditId;

    @Column(name = "authorization_audit_id", nullable = false)
    Long authorizationAuditId;
}
