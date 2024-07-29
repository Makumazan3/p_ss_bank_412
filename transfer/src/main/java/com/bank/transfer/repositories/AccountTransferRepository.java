package com.bank.transfer.repositories;

import com.bank.transfer.entity.transfers.AccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс-репозиторий для работы с сущностями AccountTransfer.
 **/

@Repository
public interface AccountTransferRepository extends JpaRepository<AccountTransfer, Long> {
}
