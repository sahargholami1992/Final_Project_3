package com.example.final_project_3.mapper;

import com.example.final_project_3.entity.SubService;
import com.example.final_project_3.dto.SubServiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface SubServiceMapper {
    SubServiceMapper INSTANCE = Mappers.getMapper(SubServiceMapper.class);
    Collection<SubServiceResponse> convertCollectionsToDto(Collection<SubService> subServices);
}
