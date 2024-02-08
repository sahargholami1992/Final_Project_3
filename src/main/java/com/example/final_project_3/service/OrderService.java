package com.example.final_project_3.service;




import com.example.final_project_3.entity.Customer;
import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.entity.SubService;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.dto.OrderDto;

import java.util.Collection;

public interface OrderService  {
    Order addOrder(OrderDto dto, Customer customer, SubService subService);
    Collection<Order> getPendingOrdersForExpert(Expert expert);

    void UpdateStatus(Order order);
    void changeOrderStatus(Order order, StatusOrder statusOrder);
    Collection<Order> findAll();

    void update(Order order);

    boolean existById(Integer orderId);

    Order findById(Integer orderId);
}
