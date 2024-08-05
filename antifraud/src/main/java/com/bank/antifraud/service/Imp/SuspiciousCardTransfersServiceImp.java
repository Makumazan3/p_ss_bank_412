package com.bank.antifraud.service.Imp;

import com.bank.antifraud.dto.SuspiciousCardTransfersDto;
import com.bank.antifraud.entity.SuspiciousCardTransfersEntity;
import com.bank.antifraud.exception_handling.NoSuchTransferException;
import com.bank.antifraud.mappers.SuspiciousCardTransfersMapper;
import com.bank.antifraud.repository.SuspiciousCardTransfersRepository;
import com.bank.antifraud.service.SuspiciousCardTransfersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class SuspiciousCardTransfersServiceImp implements SuspiciousCardTransfersService {

    private final SuspiciousCardTransfersRepository suspiciousCardTransfersRepository;
    private final SuspiciousCardTransfersMapper suspiciousCardTransfersMapper;




    @Override
    public List<SuspiciousCardTransfersDto> getAllSuspiciousCardTransfers() {
        return suspiciousCardTransfersMapper.toDtoList(suspiciousCardTransfersRepository.findAll());
    }

    @Override
    public SuspiciousCardTransfersDto getSuspiciousCardTransfersById(Long id) {
        return suspiciousCardTransfersMapper.toDto(suspiciousCardTransfersRepository.findById(id)
                .orElseThrow(() -> {
                    final String exceptionMessage =
                            String.format("SuspiciousCardTransfers not found with ID = %d", id);
                    final String logMessage = String.format("The request failed. " +
                            "NoSuchTransferException was thrown. %s", exceptionMessage);
                    final NoSuchTransferException exception = new NoSuchTransferException(exceptionMessage);
                    log.warn(logMessage);
                    log.debug(logMessage, exception);

                    return exception;
                }));
    }

    @Override
    public SuspiciousCardTransfersDto addSuspiciousCardTransfers(SuspiciousCardTransfersDto suspiciousCardTransfersDto) {
        return suspiciousCardTransfersMapper.toDto(suspiciousCardTransfersRepository
                .save(suspiciousCardTransfersMapper.toEntity(suspiciousCardTransfersDto)));
    }


    @Override

    public SuspiciousCardTransfersDto updateSuspiciousCardTransfers(Long id, SuspiciousCardTransfersDto updatedSuspiciousCardTransfersDto) {
        // Логирование начала обновления
        log.info("Attempting to update SuspiciousCardTransfer with ID = {}", id);

        // Найти существующую запись по идентификатору
        SuspiciousCardTransfersEntity existingEntity = suspiciousCardTransfersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SuspiciousCardTransfer with ID " + id + " not found"));

        // Обновить поля существующей сущности на основе данных из DTO
        existingEntity.setCardTransferId(updatedSuspiciousCardTransfersDto.getCardTransferId());
        existingEntity.setBlocked(updatedSuspiciousCardTransfersDto.isBlocked());
        existingEntity.setSuspicious(updatedSuspiciousCardTransfersDto.isSuspicious());
        existingEntity.setBlockedReason(updatedSuspiciousCardTransfersDto.getBlockedReason());
        existingEntity.setSuspiciousReason(updatedSuspiciousCardTransfersDto.getSuspiciousReason());

        // Сохранить обновленную сущность
        SuspiciousCardTransfersEntity updatedEntity = suspiciousCardTransfersRepository.save(existingEntity);

        // Логирование успешного обновления
        log.info("Successfully updated SuspiciousCardTransfer with ID = {}", id);

        // Вернуть обновленный DTO
        return suspiciousCardTransfersMapper.toDto(updatedEntity);
    }


    @Override
    public SuspiciousCardTransfersDto deleteSuspiciousCardTransfersById(Long id) {
        SuspiciousCardTransfersDto suspiciousCardTransfersDto = this.getSuspiciousCardTransfersById(id);
        suspiciousCardTransfersRepository.deleteById(id);
        return suspiciousCardTransfersDto;
    }
}
