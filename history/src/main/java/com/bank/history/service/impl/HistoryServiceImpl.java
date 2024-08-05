package com.bank.history.service.impl;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.expHandler.NotFoundException;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import com.bank.history.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository repository;
    private final HistoryMapper mapper;
    public NotFoundException notFoundException;

    @Override
    public HistoryEntity create(HistoryDto dto) {
        return repository.save(mapper.toEntity(dto));
    }
    @Override
    public HistoryEntity readById(Long id) {
        return repository.findById(id).orElseThrow(() -> notFoundException.getEntityNotFoundException("not found"));
    }
    @Override
    public List<HistoryEntity> readAll() {
        return repository.findAll();
    }
    @Override
    public HistoryEntity update(Long id, HistoryDto dto) {
        HistoryEntity entity = repository.findById(id).orElseThrow(() -> notFoundException.getEntityNotFoundException("not found"));
        return repository.save(mapper.mergeToEntity(dto, entity));
    }
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}


