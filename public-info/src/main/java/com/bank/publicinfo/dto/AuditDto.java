package com.bank.publicinfo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditDto {

    private Long id;
    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String newEntityJson;
    private String entityJson;
}