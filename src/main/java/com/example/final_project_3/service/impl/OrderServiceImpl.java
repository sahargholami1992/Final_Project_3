package com.example.final_project_3.service.impl;


import com.example.final_project_3.entity.Customer;
import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.entity.SubService;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.repository.OrderRepository;
import com.example.final_project_3.service.OrderService;
import com.example.final_project_3.service.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    protected final OrderRepository repository;
    @Transactional
    @Override
    public Order registerOrder(OrderDto dto, Customer customer, SubService subService) {
        if (dto.getRecommendedPrice() > subService.getBasePrice() && dto.getDateDoOrder().isAfter(LocalDate.now())) {
            Order order = new Order();
            order.setCustomer(customer);
            order.setSubService(subService);
            order.setAddress(dto.getAddress());
            order.setRecommendedPrice(dto.getRecommendedPrice());
            order.setDescription(dto.getDescription());
            order.setDateDoOrder(dto.getDateDoOrder());
            order.setStatusOrder(dto.getStatusOrder());
            repository.save(order);
            return order;
        }else throw new NoSuchElementException(" time or price in not valid");

    }

    public Collection<Order> getPendingOrdersForExpert(Expert expert) {
        return repository.findOrderByForExpert(expert).stream()
                .filter(order -> order.getStatusOrder() == StatusOrder.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS ||
                        order.getStatusOrder() == StatusOrder.WAITING_FOR_EXPERT_SELECTION)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public void UpdateStatus(Order order) {
        repository.save(order);
    }
    @Transactional
    @Override
    public void changeOrderStatus(Order order,StatusOrder statusOrder) {
        order.setStatusOrder(statusOrder);
        repository.save(order);
    }

    @Override
    public Collection<Order> findAll() {
        return repository.findAll();
    }
}
