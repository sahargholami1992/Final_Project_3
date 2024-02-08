package com.example.final_project_3.mapper;

import com.example.final_project_3.entity.BaseUser;
import com.example.final_project_3.dto.SearchUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    List<SearchUserResponse> convertToDto(List<BaseUser> users);

}
