package com.example.final_project_3.mapper;

import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.dto.ExpertRegisterDto;
import com.example.final_project_3.dto.ExpertResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface ExpertsMapper {
    ExpertsMapper INSTANCE = Mappers.getMapper(ExpertsMapper.class);
    Expert convertToEntity(ExpertRegisterDto dto);
    ExpertResponseDto convertToResponse(Expert expert);
    Collection<ExpertResponseDto> convertToDto(Collection<Expert> experts);
}
