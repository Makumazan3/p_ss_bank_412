package com.bank.antifraud.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="audit", schema = "anti_fraud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column ( name = "entity_type",length = 40,nullable = false)
    private String entityType;

    @Column (name = "operation_type", length = 255, nullable = false)
    private String operationType;

    @Column (name = "created_by",length = 255,nullable = false)
    private String createdBy;

    @Column (name= "modified_by",length = 255)
    private String modifiedBy;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column (name = "new_entity_json")
    private String newEntityJson;

    @Column(name = "entity_json",nullable = false)
    private String entityJson;


}
