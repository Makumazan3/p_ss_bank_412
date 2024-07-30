package com.bank.transfer.services.accountTransferService;

import com.bank.transfer.dto.transfersDto.AccountTransferDto;
import com.bank.transfer.mappers.AccountTransferMapper;
import com.bank.transfer.repositories.AccountTransferRepository;
import com.bank.transfer.audits.auditAccountTransfer.CreateAuditableAccountTransfer;
import com.bank.transfer.audits.auditAccountTransfer.UpdateAuditableAccountTransfer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Класс, имплементирующий сервис AccountTransferService.
 * Обеспечивает функциональность создания, получения, обновления
 * и удаления записей AccountTransfer.
 **/

@Service
@AllArgsConstructor
@Transactional (readOnly = true)
public class AccountTransferServiceImpl implements AccountTransferService {

    private final AccountTransferRepository accountTransferRepository;

    private final AccountTransferMapper accountTransferMapper;

    private final Logger logger = LoggerFactory.getLogger(AccountTransferServiceImpl.class);

    @Override
    public List<AccountTransferDto> getAllAccountTransfer() {
        logger.info("Старт сервис-метода getAllAccountTransfer");
        return accountTransferMapper.toDtoList(accountTransferRepository.findAll());
    }

    @Override
    public AccountTransferDto getAccountTransferById(Long accountTransferId) {
        logger.info("Старт сервис-метода getAccountTransferById");
        return accountTransferMapper.toDto(accountTransferRepository.getReferenceById(accountTransferId));
    }

    @Override
    @Transactional
    @CreateAuditableAccountTransfer
    public void createAccountTransfer(AccountTransferDto accountTransferDto) {
        logger.info("Старт сервис-метода createAccountTransfer");
        accountTransferRepository.save(accountTransferMapper.toEntity(accountTransferDto));
    }

    @Override
    @Transactional
    @UpdateAuditableAccountTransfer
    public void updateAccountTransfer(AccountTransferDto accountTransferDto) {
        logger.info("Старт сервис-метода updateAccountTransfer");
        accountTransferRepository.save(accountTransferMapper.toEntity(accountTransferDto));
    }

    @Override
    @Transactional
    public void deleteAccountTransferById(long accountTransferId) {
        logger.info("Старт сервис-метода deleteAccountTransferById");
        accountTransferRepository.deleteById(accountTransferId);
    }
}
