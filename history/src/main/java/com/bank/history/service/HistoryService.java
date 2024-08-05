package com.bank.history.service;


import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;

import java.util.List;


public interface HistoryService {

    HistoryEntity create(HistoryDto dto);

    HistoryEntity readById(Long id);

    List<HistoryEntity> readAll();

    HistoryEntity update(Long id, HistoryDto dto);

    void deleteById(Long id);

}
