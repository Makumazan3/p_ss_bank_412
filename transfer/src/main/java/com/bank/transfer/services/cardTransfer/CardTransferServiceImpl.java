package com.bank.transfer.services.cardTransfer;

import com.bank.transfer.dto.transfersDto.CardTransferDto;
import com.bank.transfer.mappers.CardTransferMapper;
import com.bank.transfer.repositories.CardTransferRepository;
import com.bank.transfer.utils.auditCardTransfer.AspectActionTypeCardTransfer;
import com.bank.transfer.utils.auditCardTransfer.AuditableCardTransfer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Класс, имплементирующий сервис CardTransferService.
 * Обеспечивает функциональность создания, получения, обновления
 * и удаления записей CardTransfer.
 **/

@Service
@AllArgsConstructor
public class CardTransferServiceImpl implements CardTransferService {

    private final CardTransferRepository cardTransferRepository;

    private final CardTransferMapper cardTransferMapper;
    private final Logger logger = LoggerFactory.getLogger(CardTransferServiceImpl.class);

    @Override
    public List<CardTransferDto> getAllCardTransfers() {
        logger.info("Старт сервис-метода getAllCardTransfers");
        return cardTransferMapper.toDtoList(cardTransferRepository.findAll());
    }

    @Override
    public CardTransferDto getCardTransferById(Long cardTransferId) {
        logger.info("Старт сервис-метода getCardTransferById");
        return cardTransferMapper.toDto(cardTransferRepository.getReferenceById(cardTransferId));
    }

    @Override
    @AuditableCardTransfer(auditActionType = AspectActionTypeCardTransfer.CREATE_CARD)
    public void createCardTransfer(CardTransferDto cardTransferDto) {
        logger.info("Старт сервис-метода createCardTransfer");
        cardTransferRepository.save(cardTransferMapper.toEntity(cardTransferDto));
    }

    @Override
    @AuditableCardTransfer(auditActionType = AspectActionTypeCardTransfer.UPDATE_CARD)
    public void updateCardTransfer(CardTransferDto cardTransferDto) {
        logger.info("Старт сервис-метода updateCardTransfer");
        cardTransferRepository.save(cardTransferMapper.toEntity(cardTransferDto));
    }


    @Override
    @AuditableCardTransfer(auditActionType = AspectActionTypeCardTransfer.DELETE_CARD)
    public void deleteCardTransferById(long cardTransferId) {
        logger.info("Старт сервис-метода updateCardTransferById");
        cardTransferRepository.deleteById(cardTransferId);
    }
}
