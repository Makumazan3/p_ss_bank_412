package com.bank.transfer.repositories;

import com.bank.transfer.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс-репозиторий для работы с сущностями Audit.
 **/

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
}
