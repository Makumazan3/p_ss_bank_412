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
        if (atmDto.getAddress() == null) {
            throw new NullPointerException("This field can't be null!!");
        }

        final Atm newAtm;
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
        Atm atm = atmRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Atm not found with id: " + id));
        return atmMapper.toDto(atm);
    }

    @Override
    @Transactional
    public AtmDto updateAtm(AtmDto atmDto) {
        if (atmDto.getAddress() == null) {
            throw new NullPointerException("This field can't be null!!");
        }
        Atm atm = atmRepository.save(atmMapper.toEntity(atmDto));
        return atmMapper.toDto(atm);
    }

    @Override
    @Transactional
    public void deleteAtm(Long id) {
        if (!atmRepository.existsById(id)) {
            throw new NullPointerException("Atm not found with id: " + id);
        }
        atmRepository.deleteById(id);
    }
}