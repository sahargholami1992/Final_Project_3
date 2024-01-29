package com.example.final_project_3.service;





import com.example.final_project_3.entity.*;
import com.example.final_project_3.service.dto.CustomerRegisterDto;
import com.example.final_project_3.service.user.UserService;

import java.util.Collection;

public interface CustomerService extends UserService<Customer> {
    Customer registerCustomer(CustomerRegisterDto dto);
    Collection<BasicService> showAllService();
    Collection<SubService> showAllSubServiceByService(BasicService basicService);
    void changeOrderStatusToStarted(Order order, Offer offer);
    void changeOrderStatusToDone(Order order);
    void changeOrderStatusWaitingForExpertToComeToYourPlace(Offer offer);
    Collection<Offer> findAllByOrder(Customer customer);
}
