package com.example.final_project_3.mapper;

import com.example.final_project_3.entity.Order;
import com.example.final_project_3.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderResponse convertToResponse(Order order);
    Collection<OrderResponse > convertToCollectionDto(Collection<Order> orders);

}
