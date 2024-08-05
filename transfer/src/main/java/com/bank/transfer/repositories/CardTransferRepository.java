package com.bank.transfer.repositories;

import com.bank.transfer.entity.transfers.CardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс-репозиторий для работы с сущностями CardTransfer.
 **/

@Repository
public interface CardTransferRepository extends JpaRepository<CardTransfer, Long> {
}
