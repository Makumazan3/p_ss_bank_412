package com.bank.transfer.controllers;

import com.bank.transfer.dto.transfersDto.AccountTransferDto;
import com.bank.transfer.services.accountTransferService.AccountTransferService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.validation.Valid;
import java.util.List;

/**
 * Класс-контроллер для обработки ввода и
 * предоставления информации связанной с AccountTransfer.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * создания, получения, обновления и удаления записей
 * об AccountTransfer.
 **/

@RestController
@AllArgsConstructor

public class AccountTransferRestController {

    private final AccountTransferService accountTransferService;
    private final Logger logger = LoggerFactory.getLogger(AccountTransferRestController.class);

    /**
     * Метод, предоставляющий информацию
     * о всех записях AccountTransfer из БД.
     * @return возвращает информацию
     * о всех записях AccountTransfer из БД в формате DTO.
     **/

    @GetMapping("/account-transfer/getAll")
    public List<AccountTransferDto> getAllAccountTransfers() {
        logger.info("Старт контроллер-метода getAllAccountTransfers");
        return accountTransferService.getAllAccountTransfer();
    }

    /**
     * Метод, предоставляющий информацию
     * об определённой записи AccountTransfer из БД по id.
     * @return возвращает информацию
     * о записи AccountTransfer из БД по id в формате DTO.
     **/

    @GetMapping("/account-transfer/get/{id}")
    public AccountTransferDto getAccountTransfer(@PathVariable long id) {
        logger.info("Старт контроллер-метода getAccountTransfer");
        return accountTransferService.getAccountTransferById(id);
    }

    /**
     * Метод, создающий новую запись
     * о AccountTransfer в БД.
     * @param accountTransferDto - это объект DTO.
     **/

    @PostMapping("/account-transfer/create")
    public void createAccountTransfer(@Valid @RequestBody AccountTransferDto accountTransferDto) {
        logger.info("Старт контроллер-метода createAccountTransfer");
        accountTransferService.createAccountTransfer(accountTransferDto);
    }

    /**
     * Метод, редактирующий существующую запись
     * о AccountTransfer в БД.
     * @param accountTransferDto - это объект DTO.
     **/

    @PatchMapping("/account-transfer/update")
    public void updateAccountTransfer(@Valid @RequestBody AccountTransferDto accountTransferDto) {
        logger.info("Старт контроллер-метода updateAccountTransfer");
        accountTransferService.updateAccountTransfer(accountTransferDto);
    }

    /**
     * Метод, удаляющий запись
     * о AccountTransfer в БД.
     * @param id - это поиск id AccountTransfer.
     **/

    @DeleteMapping("/account-transfer/delete/{id}")
    public void deleteAccountTransfer(@PathVariable long id) {
        logger.info("Старт контроллер-метода deleteAccountTransfer");
        accountTransferService.deleteAccountTransferById(id);
    }

}
