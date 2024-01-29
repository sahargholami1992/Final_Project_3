package com.example.final_project_3.impl;


import com.example.final_project_3.entity.Customer;
import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.entity.SubService;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.service.*;
import com.example.final_project_3.service.dto.CustomerRegisterDto;
import com.example.final_project_3.service.dto.ExpertRegisterDto;
import com.example.final_project_3.service.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SubServiceService subServiceService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ExpertService expertService;

    @Test
    @Transactional
    void registerOrder_WhenValidDto_ShouldSaveOrder() {
        // Arrange
        ExpertRegisterDto expertRegisterDto = new ExpertRegisterDto("sgdsg","jsa","expertt@emali.com","Expert12","3.jpg".getBytes());
        Expert expert = expertService.registerExpert(expertRegisterDto);
        adminService.changeExpertStatus(expert);
        CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto("dsd","dss","customerr@email.com","Custom12");
        Customer customer = customerService.registerCustomer(customerRegisterDto);
        SubService subService = new SubService();
        subService.setBasePrice(100);
        subService.setSubServiceName("service name first");
        subServiceService.saveOrUpdate(subService);
        OrderDto validDto = new OrderDto("shgdhadg",200,"shjsdhjah",LocalDate.now().plusDays(2));

        // Act
        assertDoesNotThrow(() -> orderService.registerOrder(validDto, customer, subService));

        // Assert
        Collection<Order> orders = orderService.findAll();
        assertEquals(2, orders.size());
    }

    @Test
    void registerOrder_WhenInvalidDtoPrice_ShouldThrowNoResultException() {
        // Arrange
        CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto("dsd","dss","customert@email.com","Custom12");
        Customer customer = customerService.registerCustomer(customerRegisterDto);
        SubService subService = new SubService();
        subService.setSubServiceName("service name");
        subService.setBasePrice(30.0); // Set a base price for the subService
        OrderDto invalidDto = new OrderDto("hgghgf",20.0,"hsdgajhg",LocalDate.now().plusDays(1));

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> orderService.registerOrder(invalidDto, customer, subService));
    }

    @Test
    void registerOrder_WhenInvalidDtoDate_ShouldThrowNoResultException() {
        // Arrange
        CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto("dsd","dss","customer12@email.com","Custom12");
        Customer customer = customerService.registerCustomer(customerRegisterDto);
        SubService subService = new SubService();
        subService.setSubServiceName("service name");
        subService.setBasePrice(30.0); // Set a base price for the subService
        OrderDto invalidDto = new OrderDto("dsdd",40.0,"dsdd",LocalDate.now().minusDays(1));
        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> orderService.registerOrder(invalidDto, customer, subService));
    }

    @Test
    void getPendingOrdersForExpert_ShouldReturnPendingOrders() {
        // Arrange
        ExpertRegisterDto expertRegisterDto = new ExpertRegisterDto("sgdsg","jsa","expert21@emali.com","Expert12","3.jpg".getBytes());
        Expert expert = expertService.registerExpert(expertRegisterDto);
        adminService.changeExpertStatus(expert);
        CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto("dsd","dss","customer43@email.com","Custom12");
        Customer customer = customerService.registerCustomer(customerRegisterDto);
        SubService subService = new SubService();
        subService.setBasePrice(100);
        subService.setSubServiceName("service name");
        subServiceService.saveOrUpdate(subService);
        adminService.saveExpertForSubService(subService,expert);
        OrderDto validDto = new OrderDto("shgdhadg",200,"shjsdhjah",LocalDate.now().plusDays(2));
        Order order = orderService.registerOrder(validDto, customer, subService);
        orderService.changeOrderStatus(order, StatusOrder.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS);

        // Act
        Collection<Order> pendingOrders = orderService.getPendingOrdersForExpert(expert);

        // Assert
        assertEquals(1, pendingOrders.size());
        assertEquals(StatusOrder.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, pendingOrders.iterator().next().getStatusOrder());
    }

}
