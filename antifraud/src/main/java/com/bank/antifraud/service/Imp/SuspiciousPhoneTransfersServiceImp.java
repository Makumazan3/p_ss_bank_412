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
        log.info("Получение всех подозрительных переводов по телефонам");
        return suspiciousPhoneTransfersMapper.toDtoList(suspiciousPhoneTransfersRepository.findAll());
    }

    @Override
    public SuspiciousPhoneTransfersDto getSuspiciousPhoneTransfersById(Long id) {
        log.info("Получение подозрительного перевода по телефону с ID = {}", id);
        return suspiciousPhoneTransfersMapper.toDto(suspiciousPhoneTransfersRepository.findById(id)
                .orElseThrow(() -> {
                    final String exceptionMessage =
                            String.format("Подозрительный перевод по телефону не найден с ID = %d", id);
                    final String logMessage = String.format("Запрос не выполнен. " +
                            "Брошено исключение NoSuchTransferException. %s", exceptionMessage);
                    final NoSuchTransferException exception = new NoSuchTransferException(exceptionMessage);
                    log.warn(logMessage);
                    log.debug(logMessage, exception);

                    return exception;
                }));
    }

    @Override
    public SuspiciousPhoneTransfersDto addSuspiciousPhoneTransfers(SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto) {
        log.info("Добавление нового подозрительного перевода по телефону.");
        return suspiciousPhoneTransfersMapper.toDto(suspiciousPhoneTransfersRepository
                .save(suspiciousPhoneTransfersMapper.toEntity(suspiciousPhoneTransfersDto)));
    }


    @Override

    public SuspiciousPhoneTransfersDto updateSuspiciousPhoneTransfers(Long id, SuspiciousPhoneTransfersDto updatedSuspiciousPhoneTransfersDto) {
        log.info("\"Попытка обновления подозрительного перевода по телефону с ID = {}", id);

        SuspiciousPhoneTransfersEntity existingEntity = suspiciousPhoneTransfersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Подозрительный перевод по телефону с ID " + id + " не найден"));

        existingEntity.setPhoneTransferId(updatedSuspiciousPhoneTransfersDto.getPhoneTransferId());
        existingEntity.setBlocked(updatedSuspiciousPhoneTransfersDto.isBlocked());
        existingEntity.setSuspicious(updatedSuspiciousPhoneTransfersDto.isSuspicious());
        existingEntity.setBlockedReason(updatedSuspiciousPhoneTransfersDto.getBlockedReason());
        existingEntity.setSuspiciousReason(updatedSuspiciousPhoneTransfersDto.getSuspiciousReason());

        SuspiciousPhoneTransfersEntity updatedEntity = suspiciousPhoneTransfersRepository.save(existingEntity);
        log.info("Подозрительный перевод по телефону успешно обновлен с ID = {}", id);
        return suspiciousPhoneTransfersMapper.toDto(updatedEntity);
    }

    @Override
    public SuspiciousPhoneTransfersDto deleteSuspiciousPhoneTransfersById(Long id) {
        log.info("Попытка удаления подозрительного перевода по телефону с ID = {}", id);
        SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto = this.getSuspiciousPhoneTransfersById(id);
        suspiciousPhoneTransfersRepository.deleteById(id);
        log.info("Подозрительный перевод по телефону успешно удален с ID = {}", id);
        return suspiciousPhoneTransfersDto;
    }
}
