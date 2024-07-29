package com.bank.transfer.repositories;

import com.bank.transfer.entity.transfers.PhoneTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс-репозиторий для работы с сущностями PhoneTransfer.
 **/

@Repository
public interface PhoneTransferRepository extends JpaRepository <PhoneTransfer, Long> {
}
