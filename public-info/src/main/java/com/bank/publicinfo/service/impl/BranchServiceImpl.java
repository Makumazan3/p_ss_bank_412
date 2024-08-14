package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.mappers.BranchMapper;
import com.bank.publicinfo.model.Branch;
import com.bank.publicinfo.repositories.BranchRepository;
import com.bank.publicinfo.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    @Transactional
    public BranchDto addBranch(BranchDto branchDto) {
        final Branch newBranch;
        try {
            newBranch = branchRepository.save(branchMapper.toEntity(branchDto));
        } catch (Exception e) {
            log.error("Your request is bad!!!! Try again!!!", e.getMessage());
            throw new RuntimeException(e);
        }
        return branchMapper.toDto(newBranch);
    }

    @Override
    public List<BranchDto> getAllBranch() {
        return branchMapper.toDtoList(branchRepository.findAll());
    }

    @Override
    public BranchDto getBranchById(Long id) {
        return branchMapper.toDto(branchRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public void updateBranch(BranchDto branchDto) {
        branchRepository.save(branchMapper.toEntity(branchDto));
    }

    @Override
    @Transactional
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }

}
