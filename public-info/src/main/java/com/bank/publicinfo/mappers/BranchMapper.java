package com.bank.publicinfo.mappers;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.model.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BranchMapper {

    BranchDto toDto(Branch branch);

    Branch toEntity(BranchDto branchDto);

    List<BranchDto> toDtoList(List<Branch> branches);
}