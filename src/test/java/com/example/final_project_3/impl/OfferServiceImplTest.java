package com.example.final_project_3.impl;


import com.example.final_project_3.entity.*;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.service.*;
import com.example.final_project_3.service.dto.CustomerRegisterDto;
import com.example.final_project_3.service.dto.ExpertRegisterDto;
import com.example.final_project_3.service.dto.OfferDto;
import com.example.final_project_3.service.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OfferServiceImplTest {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SubServiceService subServiceService;
    @Autowired
    private AdminService adminService;
    Expert expert;
    Customer customer;

    @Test
    void saveOffer_WhenValidDto_ShouldSaveOffer() {
        // Arrange
        ExpertRegisterDto expertRegisterDto = new ExpertRegisterDto("sgdsg","jsa","expert@emali.com","Expert12","3.jpg".getBytes());
        expert = expertService.registerExpert(expertRegisterDto);
        adminService.changeExpertStatus(expert);
        CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto("dsd","dss","customer@email.com","Custom12");
        customer = customerService.registerCustomer(customerRegisterDto);
        SubService subService = new SubService();
        subService.setBasePrice(100);
        subService.setSubServiceName("sub service name");
        subServiceService.saveOrUpdate(subService);
        OrderDto orderDto = new OrderDto("dssd",200,"dsds",LocalDate.now().plusDays(2));
        Order order = orderService.registerOrder(orderDto,customer, subService);
        OfferDto validDto = new OfferDto();
        validDto.setRecommendedPrice(400.0);
        validDto.setDurationOfWork(5);
        validDto.setSuggestedTimeToStartWork(LocalDate.now().plusDays(2));
        validDto.setDateRegisterOffer(LocalDate.now());

        // Act
        assertDoesNotThrow(() -> offerService.saveOffer(expert, order, validDto));

        // Assert
        Collection<Offer> offers = offerService.getOffersForOrder(order.getCustomer(),"price");
        assertEquals(1, offers.size());


        assertEquals(StatusOrder.WAITING_FOR_EXPERT_SELECTION, order.getStatusOrder());
    }

    @Test
    void saveOffer_WhenInvalidDtoPrice_ShouldThrowIllegalArgumentException() {
        // Arrange
        SubService subService = new SubService();
        subService.setBasePrice(100);
        subService.setSubServiceName("another service name");
        subServiceService.saveOrUpdate(subService);
        OrderDto orderDto1 = new OrderDto("dsss",250,"dsffds",LocalDate.now().plusDays(2));
        Order order1 = orderService.registerOrder(orderDto1,customer, subService);
        order1.setStatusOrder(StatusOrder.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS);

        OfferDto invalidDto = new OfferDto();
        invalidDto.setRecommendedPrice(30.0);
        invalidDto.setDurationOfWork(5);
        invalidDto.setSuggestedTimeToStartWork(LocalDate.now().plusDays(2));
        invalidDto.setDateRegisterOffer(LocalDate.now());
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> offerService.saveOffer(expert, order1, invalidDto));
    }

    @Test
    void saveOffer_WhenInvalidDtoTime_ShouldThrowIllegalArgumentException() {
        // Arrange
        SubService subService = new SubService();
        subService.setBasePrice(100);
        subService.setSubServiceName("service name");
        subServiceService.saveOrUpdate(subService);
        OrderDto orderDto = new OrderDto("dsss",250,"dsffds",LocalDate.now().plusDays(2));
        Order order = orderService.registerOrder(orderDto,customer, subService);
        order.setStatusOrder(StatusOrder.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS);
        order.setSubService(new SubService());
        OfferDto invalidDto = new OfferDto();
        invalidDto.setRecommendedPrice(40.0);
        invalidDto.setDurationOfWork(5);
        invalidDto.setSuggestedTimeToStartWork(LocalDate.now().minusDays(1));
        invalidDto.setDateRegisterOffer(LocalDate.now());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> offerService.saveOffer(expert, order, invalidDto));
    }


//    @Test
//    void getOffersForOrder_ShouldReturnSortedOffersByPrice() {
//        // Arrange
//        Order order = new Order();
//        Offer offer1 = new Offer();
//        offer1.setRecommendedPrice(30.0);
//        Offer offer2 = new Offer();
//        offer2.setRecommendedPrice(20.0);
//        order.setOffers(List.of(offer1, offer2));
//
//        // Act
//        List<Offer> sortedOffers = offerService.getOffersForOrder(order, "price");
//
//        // Assert
//        assertEquals(2, sortedOffers.size());
//        assertEquals(20.0, sortedOffers.get(0).getRecommendedPrice());
//        assertEquals(30.0, sortedOffers.get(1).getRecommendedPrice());
//    }

//    @Test
//    void getOffersForOrder_ShouldReturnSortedOffersByRating() {
//        // Arrange
//        Order order = new Order();
//        Offer offer1 = new Offer();
//        offer1.setExpert(new Expert("Expert1", 4));
//        Offer offer2 = new Offer();
//        offer2.setExpert(new Expert("Expert2", 3));
//        order.setOffers(List.of(offer1, offer2));
//
//        // Act
//        List<Offer> sortedOffers = offerService.getOffersForOrder(order, "rating");
//
//        // Assert
//        assertEquals(2, sortedOffers.size());
//        assertEquals("Expert2", sortedOffers.get(0).getExpert().getFirstName());
//        assertEquals("Expert1", sortedOffers.get(1).getExpert().getFirstName());
//    }


}
