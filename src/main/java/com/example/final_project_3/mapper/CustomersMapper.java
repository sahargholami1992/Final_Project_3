package com.example.final_project_3.mapper;

import com.example.final_project_3.entity.Customer;
import com.example.final_project_3.dto.CustomerRegisterDto;
import com.example.final_project_3.dto.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomersMapper {
    CustomersMapper INSTANCE = Mappers.getMapper(CustomersMapper.class);
    Customer convertToEntity(CustomerRegisterDto dto);
    CustomerResponse convertToDto(Customer customer);
}
