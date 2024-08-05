package com.bank.antifraud.service.Imp;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransfersEntity;
import com.bank.antifraud.exception_handling.NoSuchTransferException;
import com.bank.antifraud.mappers.SuspiciousPhoneTransfersMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransfersRepository;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class SuspiciousPhoneTransfersServiceImp implements SuspiciousPhoneTransfersService {

    private final SuspiciousPhoneTransfersRepository suspiciousPhoneTransfersRepository;
    private final SuspiciousPhoneTransfersMapper suspiciousPhoneTransfersMapper;




    @Override
    public List<SuspiciousPhoneTransfersDto> getAllSuspiciousPhoneTransfers() {
        return suspiciousPhoneTransfersMapper.toDtoList(suspiciousPhoneTransfersRepository.findAll());
    }

    @Override
    public SuspiciousPhoneTransfersDto getSuspiciousPhoneTransfersById(Long id) {
        return suspiciousPhoneTransfersMapper.toDto(suspiciousPhoneTransfersRepository.findById(id)
                .orElseThrow(() -> {
                    final String exceptionMessage =
                            String.format("SuspiciousPhoneTransfers not found with ID = %d", id);
                    final String logMessage = String.format("The request failed. " +
                            "NoSuchTransferException was thrown. %s", exceptionMessage);
                    final NoSuchTransferException exception = new NoSuchTransferException(exceptionMessage);
                    log.warn(logMessage);
                    log.debug(logMessage, exception);

                    return exception;
                }));
    }

    @Override
    public SuspiciousPhoneTransfersDto addSuspiciousPhoneTransfers(SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto) {
        return suspiciousPhoneTransfersMapper.toDto(suspiciousPhoneTransfersRepository
                .save(suspiciousPhoneTransfersMapper.toEntity(suspiciousPhoneTransfersDto)));
    }


    @Override

    public SuspiciousPhoneTransfersDto updateSuspiciousPhoneTransfers(Long id, SuspiciousPhoneTransfersDto updatedSuspiciousPhoneTransfersDto) {
        // Логирование начала обновления
        log.info("Attempting to update SuspiciousPhoneTransfers with ID = {}", id);

        // Найти существующую запись по идентификатору
        SuspiciousPhoneTransfersEntity existingEntity = suspiciousPhoneTransfersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SuspiciousPhoneTransfers with ID " + id + " not found"));

        // Обновить поля существующей сущности на основе данных из DTO
        existingEntity.setPhoneTransferId(updatedSuspiciousPhoneTransfersDto.getPhoneTransferId());
        existingEntity.setBlocked(updatedSuspiciousPhoneTransfersDto.isBlocked());
        existingEntity.setSuspicious(updatedSuspiciousPhoneTransfersDto.isSuspicious());
        existingEntity.setBlockedReason(updatedSuspiciousPhoneTransfersDto.getBlockedReason());
        existingEntity.setSuspiciousReason(updatedSuspiciousPhoneTransfersDto.getSuspiciousReason());

        // Сохранить обновленную сущность
        SuspiciousPhoneTransfersEntity updatedEntity = suspiciousPhoneTransfersRepository.save(existingEntity);

        // Логирование успешного обновления
        log.info("Successfully updated SuspiciousPhoneTransfers with ID = {}", id);

        // Вернуть обновленный DTO
        return suspiciousPhoneTransfersMapper.toDto(updatedEntity);
    }


    @Override
    public SuspiciousPhoneTransfersDto deleteSuspiciousPhoneTransfersById(Long id) {
        SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto = this.getSuspiciousPhoneTransfersById(id);
        suspiciousPhoneTransfersRepository.deleteById(id);
        return suspiciousPhoneTransfersDto;
    }
}
