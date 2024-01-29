package com.example.final_project_3.impl;


import com.example.final_project_3.entity.Offer;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.repository.CustomerRepository;
import com.example.final_project_3.service.BasicServiceService;
import com.example.final_project_3.service.OrderService;
import com.example.final_project_3.service.SubServiceService;
import com.example.final_project_3.service.dto.CustomerRegisterDto;
import com.example.final_project_3.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SubServiceService subServiceService;

    @Autowired
    private BasicServiceService basicServiceService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerServiceImpl customerService;

    @Test
    void registerCustomer_ShouldSaveCustomer() {
        // Arrange
        CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto("John","Doe","john.doe@example.com","John1234");

        // Act
        customerService.registerCustomer(customerRegisterDto);

        // Assert

        assertTrue(customerService.existByEmail("john.doe@example.com"));
    }

    @Test
    void changeOrderStatusToStarted_WhenSuggestedTimeIsAfterNow_ShouldChangeOrderStatus() {
        // Arrange
        Order order = new Order();
        Offer offer = new Offer();
        offer.setSuggestedTimeToStartWork(LocalDate.now().plusDays(1));

        // Act
        customerService.changeOrderStatusToStarted(order, offer);

        // Assert
        assertEquals(StatusOrder.STARTED,order.getStatusOrder());
    }

    @Test
    void changeOrderStatusToStarted_WhenSuggestedTimeIsBeforeNow_ShouldNotChangeOrderStatus() {
        // Arrange
        Order order = new Order();
        Offer offer = new Offer();
        offer.setSuggestedTimeToStartWork(LocalDate.now().minusDays(1));

        // Act
        customerService.changeOrderStatusToStarted(order, offer);

        // Assert
        assertNotEquals(StatusOrder.STARTED,order.getStatusOrder());
    }

}
