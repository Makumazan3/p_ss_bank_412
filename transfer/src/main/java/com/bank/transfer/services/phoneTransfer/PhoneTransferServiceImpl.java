package com.bank.transfer.services.phoneTransfer;

import com.bank.transfer.dto.transfersDto.PhoneTransferDto;
import com.bank.transfer.mappers.PhoneTransferMapper;
import com.bank.transfer.repositories.PhoneTransferRepository;
import com.bank.transfer.utils.auditPhoneTransfer.AspectActionTypePhoneTransfer;
import com.bank.transfer.utils.auditPhoneTransfer.AuditablePhoneTransfer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Класс, имплементирующий сервис PhoneTransferService.
 * Обеспечивает функциональность создания, получения, обновления
 * и удаления записей PhoneTransfer.
 **/

@Service
@AllArgsConstructor
public class PhoneTransferServiceImpl implements PhoneTransferService {

    private final PhoneTransferRepository phoneTransferRepository;

    private final PhoneTransferMapper phoneTransferMapper;
    private final Logger logger = LoggerFactory.getLogger(PhoneTransferDto.class);

    @Override
    public List<PhoneTransferDto> getAllPhoneTransfers() {
        logger.info("Старт сервис-метода getAllPhoneTransfers");
        return phoneTransferMapper.toDtoList(phoneTransferRepository.findAll());
    }

    @Override
    public PhoneTransferDto getPhoneTransferById(Long phoneTransferId) {
        logger.info("Старт сервис-метода getPhoneTransferById");
        return phoneTransferMapper.toDto(phoneTransferRepository.getReferenceById(phoneTransferId));
    }


    @Override
    @AuditablePhoneTransfer(auditActionType = AspectActionTypePhoneTransfer.CREATE_PHONE)
    public void createPhoneTransfer(PhoneTransferDto phoneTransferDto) {
        logger.info("Старт сервис-метода createPhoneTransfer");
        phoneTransferRepository.save(phoneTransferMapper.toEntity(phoneTransferDto));
    }

    @Override
    @AuditablePhoneTransfer(auditActionType = AspectActionTypePhoneTransfer.UPDATE_PHONE)
    public void updatePhoneTransfer(PhoneTransferDto phoneTransferDto) {
        logger.info("Старт сервис-метода updatePhoneTransfer");
        phoneTransferRepository.save(phoneTransferMapper.toEntity(phoneTransferDto));
    }

    @Override
    @AuditablePhoneTransfer(auditActionType = AspectActionTypePhoneTransfer.DELETE_PHONE)
    public void deletePhoneTransferById(long phoneTransferId) {
        logger.info("Старт сервис-метода deletePhoneTransferById");
        phoneTransferRepository.deleteById(phoneTransferId);
    }
}
