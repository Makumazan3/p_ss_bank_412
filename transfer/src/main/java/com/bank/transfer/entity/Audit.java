package com.bank.transfer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import java.time.LocalDateTime;

/**
 * Класс-сущность "Audit".
 * Необходим для хранения информации о Audit в базе данных.
 **/

@Entity
@Table(schema = "transfer", name = "audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit implements Cloneable {
    //Первичный ключ
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    //Вид сущности
    @Column(name = "entity_type", nullable = false)
    @Size(max = 40, message = "Максимальная длинна entityType 40 символов")
    private String entityType;

    //Вид операции
    @Column(name = "operation_type", nullable = false)
    @Size(max = 255, message = "Максимальная длинна operationType 255 символов")
    private String operationType;

    //Создатель операции
    @CreatedBy
    @Column(name = "created_by", nullable = false)
    @Size(max = 255, message = "Максимальная длинна createdBy 255 символов")
    private String createdBy;

    //Кем операция изменена
    @Column(name = "modified_by")
    @Size(max = 255, message = "Максимальная длинна modifiedBy 255 символов")
    private String modifiedBy;

    //Дата создания операции
    @Column(name = "created_At", nullable = false)
    private LocalDateTime createdAt;

    //Дата изменения операции
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    //Операция в виде JSON при создании
    @Column(name = "entity_json", nullable = false)
    private String entityJSON;

    //Операция в виде JSON после изменения
    @Column(name = "new_entity_json")
    private String newEntityJSON;

    @Override
    public Audit clone() {
        try {
            return (Audit) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
