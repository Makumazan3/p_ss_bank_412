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
        log.info("Получение всех подозрительных переводов по картам");
        return suspiciousCardTransfersMapper.toDtoList(suspiciousCardTransfersRepository.findAll());
    }

    @Override
    public SuspiciousCardTransfersDto getSuspiciousCardTransfersById(Long id) {
        log.info("Получение подозрительного перевода по карте с ID = {}", id);
        return suspiciousCardTransfersMapper.toDto(suspiciousCardTransfersRepository.findById(id)
                .orElseThrow(() -> {
                    final String exceptionMessage =
                            String.format("Подозрительный перевод по карте не найден с ID = %d", id);
                    final String logMessage = String.format("Запрос не выполнен." +
                            "Брошено исключение NoSuchTransferException. %s", exceptionMessage);
                    final NoSuchTransferException exception = new NoSuchTransferException(exceptionMessage);
                    log.warn(logMessage);
                    log.debug(logMessage, exception);
                    return exception;
                }));
    }

    @Override
    public SuspiciousCardTransfersDto addSuspiciousCardTransfers(SuspiciousCardTransfersDto suspiciousCardTransfersDto) {
        log.info("Добавление нового подозрительного перевода по карте");
        return suspiciousCardTransfersMapper.toDto(suspiciousCardTransfersRepository
                .save(suspiciousCardTransfersMapper.toEntity(suspiciousCardTransfersDto)));
    }

    @Override
    public SuspiciousCardTransfersDto updateSuspiciousCardTransfers(Long id, SuspiciousCardTransfersDto updatedSuspiciousCardTransfersDto) {
        log.info("Попытка обновления подозрительного перевода по карте с ID = {}", id);

        SuspiciousCardTransfersEntity existingEntity = suspiciousCardTransfersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SuspiciousCardTransfer с ID " + id + " не найден"));
        existingEntity.setCardTransferId(updatedSuspiciousCardTransfersDto.getCardTransferId());
        existingEntity.setBlocked(updatedSuspiciousCardTransfersDto.isBlocked());
        existingEntity.setSuspicious(updatedSuspiciousCardTransfersDto.isSuspicious());
        existingEntity.setBlockedReason(updatedSuspiciousCardTransfersDto.getBlockedReason());
        existingEntity.setSuspiciousReason(updatedSuspiciousCardTransfersDto.getSuspiciousReason());

        SuspiciousCardTransfersEntity updatedEntity = suspiciousCardTransfersRepository.save(existingEntity);
        log.info("Подозрительный перевод по карте успешно обновлен с ID = {}", id);

        return suspiciousCardTransfersMapper.toDto(updatedEntity);
    }

    @Override
    public SuspiciousCardTransfersDto deleteSuspiciousCardTransfersById(Long id) {
        log.info("Попытка удаления подозрительного перевода по карте с ID = {}", id);
        SuspiciousCardTransfersDto suspiciousCardTransfersDto = this.getSuspiciousCardTransfersById(id);
        suspiciousCardTransfersRepository.deleteById(id);
        log.info("Подозрительный перевод по карте успешно удален с ID = {}", id);
        return suspiciousCardTransfersDto;
    }
}
