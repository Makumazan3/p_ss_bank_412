package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDto;

import java.util.List;

public interface BranchService {

    BranchDto addBranch(BranchDto branchDto);

    List<BranchDto> getAllBranch();

    BranchDto getBranchById(Long id);

    void updateBranch(BranchDto branchDto);

    void deleteBranch(Long id);
}