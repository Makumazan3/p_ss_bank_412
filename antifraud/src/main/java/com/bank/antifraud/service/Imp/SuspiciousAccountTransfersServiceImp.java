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
        log.info("Получение всех подозрительных переводов по счету");
        return suspiciousAccountTransfersMapper.toDtoList(suspiciousAccountTransfersRepository.findAll());
    }

    @Override
    public SuspiciousAccountTransfersDto getSuspiciousAccountTransfersById(Long id) {
        log.info("Получение подозрительного перевода по счету с ID = {}", id);
        return suspiciousAccountTransfersMapper.toDto(suspiciousAccountTransfersRepository.findById(id)
                .orElseThrow(() -> {
                    final String exceptionMessage =
                            String.format("Подозрительный перевод по счету не найден с ID = %d", id);
                    final String logMessage = String.format("Запрос не выполнен. " +
                            "NoБрошено исключение NoSuchTransferException. %s", exceptionMessage);
                    final NoSuchTransferException exception = new NoSuchTransferException(exceptionMessage);
                    log.warn(logMessage);
                    log.debug(logMessage, exception);
                    return exception;
                }));
    }

    @Override
    public SuspiciousAccountTransfersDto addSuspiciousAccountTransfers(SuspiciousAccountTransfersDto suspiciousAccountTransfersDto) {
        log.info("Добавление нового подозрительного перевода по счету");
        return suspiciousAccountTransfersMapper.toDto(suspiciousAccountTransfersRepository
                .save(suspiciousAccountTransfersMapper.toEntity(suspiciousAccountTransfersDto)));
    }


    @Override

    public SuspiciousAccountTransfersDto updateSuspiciousAccountTransfers(Long id, SuspiciousAccountTransfersDto updatedSuspiciousAccountTransfersDto) {
        log.info("Обновление подозрительного перевода по счету с ID = {}", id);

        SuspiciousAccountTransfersEntity existingEntity = suspiciousAccountTransfersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SuspiciousAccountTransfer с ID " + id + " не найден"));

        existingEntity.setAccountTransferId(updatedSuspiciousAccountTransfersDto.getAccountTransferId());
        existingEntity.setBlocked(updatedSuspiciousAccountTransfersDto.isBlocked());
        existingEntity.setSuspicious(updatedSuspiciousAccountTransfersDto.isSuspicious());
        existingEntity.setBlockedReason(updatedSuspiciousAccountTransfersDto.getBlockedReason());
        existingEntity.setSuspiciousReason(updatedSuspiciousAccountTransfersDto.getSuspiciousReason());

        SuspiciousAccountTransfersEntity updatedEntity = suspiciousAccountTransfersRepository.save(existingEntity);
        log.info("Подозрительный перевод по счету успешно обновлен с ID = {}", id);
        return suspiciousAccountTransfersMapper.toDto(updatedEntity);
    }

    @Override
    public SuspiciousAccountTransfersDto deleteSuspiciousAccountTransfersById(Long id) {
        log.info("Удаление подозрительного перевода по счету с ID = {}", id);
        SuspiciousAccountTransfersDto suspiciousAccountTransfersDto = this.getSuspiciousAccountTransfersById(id);
        suspiciousAccountTransfersRepository.deleteById(id);
        log.info("Подозрительный перевод по счету успешно удален с ID = {}", id);
        return suspiciousAccountTransfersDto;
    }
}
