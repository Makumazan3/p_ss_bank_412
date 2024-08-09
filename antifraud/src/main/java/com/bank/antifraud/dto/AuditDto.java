package com.bank.antifraud.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditDto implements Serializable {
    Long id;
    String entityType;
    String operationType;
    String createdBy;
    String modifiedBy;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    String newEntityJson;
    String entityJson;
}
