package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.mappers.AtmMapper;
import com.bank.publicinfo.model.Atm;
import com.bank.publicinfo.repositories.AtmRepository;
import com.bank.publicinfo.service.AtmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AtmServiceImpl implements AtmService {

    private final AtmRepository atmRepository;
    private final AtmMapper atmMapper;

    @Override
    @Transactional
    public AtmDto addAtm(AtmDto atmDto) {
        Atm newAtm;
        try {
            newAtm = atmRepository.save(atmMapper.toEntity(atmDto));
        } catch (Exception e) {
            log.error("Your request is bad!!!! Try again!!!", e.getMessage());
            throw new RuntimeException(e);
        }
        return atmMapper.toDto(newAtm);
    }

    @Override
    public List<AtmDto> getAllAtm() {
        return atmMapper.toDtoList(atmRepository.findAll());
    }

    @Override
    public AtmDto getAtmById(Long id) {
        Atm atm = atmRepository.getReferenceById(id);
        return atmMapper.toDto(atm);
    }

    @Override
    @Transactional
    public void updateAtm(AtmDto atmDto) {
        atmRepository.save(atmMapper.toEntity(atmDto));
    }

    @Override
    @Transactional
    public void deleteAtm(Long id) {
        atmRepository.deleteById(id);
    }

}
