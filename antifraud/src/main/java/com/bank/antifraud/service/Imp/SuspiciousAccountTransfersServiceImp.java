package com.bank.antifraud.service.Imp;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfersEntity;
import com.bank.antifraud.exception_handling.NoSuchTransferException;
import com.bank.antifraud.mappers.SuspiciousAccountTransfersMapper;
import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)


public class SuspiciousAccountTransfersServiceImp implements SuspiciousAccountTransfersService {

    private final SuspiciousAccountTransfersRepository suspiciousAccountTransfersRepository;
    private final SuspiciousAccountTransfersMapper suspiciousAccountTransfersMapper;




    @Override
    public List<SuspiciousAccountTransfersDto> getAllSuspiciousAccountTransfers() {
        return suspiciousAccountTransfersMapper.toDtoList(suspiciousAccountTransfersRepository.findAll());
    }

    @Override
    public SuspiciousAccountTransfersDto getSuspiciousAccountTransfersById(Long id) {
        return suspiciousAccountTransfersMapper.toDto(suspiciousAccountTransfersRepository.findById(id)
                .orElseThrow(() -> {
                    final String exceptionMessage =
                            String.format("SuspiciousAccountTransfers not found with ID = %d", id);
                    final String logMessage = String.format("The request failed. " +
                            "NoSuchTransferException was thrown. %s", exceptionMessage);
                    final NoSuchTransferException exception = new NoSuchTransferException(exceptionMessage);
                    log.warn(logMessage);
                    log.debug(logMessage, exception);

                    return exception;
                }));
    }

    @Override
    public SuspiciousAccountTransfersDto addSuspiciousAccountTransfers(SuspiciousAccountTransfersDto suspiciousAccountTransfersDto) {
        return suspiciousAccountTransfersMapper.toDto(suspiciousAccountTransfersRepository
                .save(suspiciousAccountTransfersMapper.toEntity(suspiciousAccountTransfersDto)));
    }


    @Override

    public SuspiciousAccountTransfersDto updateSuspiciousAccountTransfers(Long id, SuspiciousAccountTransfersDto updatedSuspiciousAccountTransfersDto) {
        // Логирование начала обновления
        log.info("Attempting to update SuspiciousAccountTransfer with ID = {}", id);

        // Найти существующую запись по идентификатору
        SuspiciousAccountTransfersEntity existingEntity = suspiciousAccountTransfersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SuspiciousAccountTransfer with ID " + id + " not found"));

        // Обновить поля существующей сущности на основе данных из DTO
        existingEntity.setAccountTransferId(updatedSuspiciousAccountTransfersDto.getAccountTransferId());
        existingEntity.setBlocked(updatedSuspiciousAccountTransfersDto.isBlocked());
        existingEntity.setSuspicious(updatedSuspiciousAccountTransfersDto.isSuspicious());
        existingEntity.setBlockedReason(updatedSuspiciousAccountTransfersDto.getBlockedReason());
        existingEntity.setSuspiciousReason(updatedSuspiciousAccountTransfersDto.getSuspiciousReason());

        // Сохранить обновленную сущность
        SuspiciousAccountTransfersEntity updatedEntity = suspiciousAccountTransfersRepository.save(existingEntity);

        // Логирование успешного обновления
        log.info("Successfully updated SuspiciousAccountTransfer with ID = {}", id);

        // Вернуть обновленный DTO
        return suspiciousAccountTransfersMapper.toDto(updatedEntity);
    }


    @Override
    public SuspiciousAccountTransfersDto deleteSuspiciousAccountTransfersById(Long id) {
        SuspiciousAccountTransfersDto suspiciousAccountTransfersDto = this.getSuspiciousAccountTransfersById(id);
        suspiciousAccountTransfersRepository.deleteById(id);
        return suspiciousAccountTransfersDto;
    }
}
