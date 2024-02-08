package com.example.final_project_3.service.impl;


import com.example.final_project_3.entity.Customer;
import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.entity.SubService;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.exceptions.NoMatchResultException;
import com.example.final_project_3.exceptions.NotFoundException;
import com.example.final_project_3.repository.OrderRepository;
import com.example.final_project_3.service.CustomerService;
import com.example.final_project_3.service.OrderService;
import com.example.final_project_3.dto.OrderDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    protected final OrderRepository repository;
    protected final CustomerService customerService;

    public OrderServiceImpl(OrderRepository repository,@Lazy CustomerService customerService) {
        this.repository = repository;
        this.customerService = customerService;
    }

    @Transactional
    @Override
    public Order addOrder(OrderDto dto, Customer customer, SubService subService) {
        if (dto.getRecommendedPrice() < subService.getBasePrice()
                && dto.getDateDoOrder().isBefore(LocalDate.now()))
            throw new NoMatchResultException(" time or price in not valid");
        Order order = getOrder(dto, customer, subService);
        return repository.save(order);
    }

    private static Order getOrder(OrderDto dto, Customer customer, SubService subService) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setSubService(subService);
        order.setAddress(dto.getAddress());
        order.setRecommendedPrice(dto.getRecommendedPrice());
        order.setDescription(dto.getDescription());
        order.setDateDoOrder(dto.getDateDoOrder());
        order.setStatusOrder(dto.getStatusOrder());
        return order;
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

    @Transactional
    @Override
    public void update(Order order) {
        repository.save(order);
    }

    @Override
    public boolean existById(Integer orderId) {
        return repository.existsById(orderId);
    }

    @Override
    public Order findById(Integer orderId) {
        return repository.findById(orderId).orElseThrow(
                () -> new NotFoundException("THIS ORDER ID IS NULL")
        );
    }
}
