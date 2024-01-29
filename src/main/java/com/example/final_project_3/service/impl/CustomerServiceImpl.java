package com.example.final_project_3.service.impl;




import com.example.final_project_3.entity.*;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.repository.CustomerRepository;
import com.example.final_project_3.service.*;
import com.example.final_project_3.service.dto.CustomerRegisterDto;
import com.example.final_project_3.service.user.UserServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl extends UserServiceImpl<Customer, CustomerRepository>
                              implements CustomerService {
    protected final SubServiceService subServiceService;
    protected final BasicServiceService basicServiceService;
    protected final OrderService orderService;
    protected final OfferService offerService;
    public CustomerServiceImpl(CustomerRepository repository, SubServiceService subServiceService, BasicServiceService basicServiceService, OrderService orderService, OfferService offerService) {
        super(repository);
        this.subServiceService  =subServiceService;
        this.basicServiceService = basicServiceService;
        this.orderService=orderService;
        this.offerService=offerService;
    }
    @Transactional
    @Override
    public Customer registerCustomer(CustomerRegisterDto dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());
        customer.setDateRegister(dto.getDateRegister());
        customer.setRoll(dto.getRoll());
        customer.setPermissions(dto.getPermission());
        repository.save(customer);
        return customer;
    }

    @Override
    public Collection<BasicService> showAllService() {
        return basicServiceService.loadAll();
    }

    @Override
    public Collection<SubService> showAllSubServiceByService(BasicService basicService) {
        return subServiceService.findByService(basicService);
    }

    @Override
    public void changeOrderStatusToStarted(Order order, Offer offer) {
        if (offer.getSuggestedTimeToStartWork().isAfter(LocalDate.now()))orderService.changeOrderStatus(order, StatusOrder.STARTED);
    }

    @Override
    public void changeOrderStatusToDone(Order order) {
        orderService.changeOrderStatus(order,StatusOrder.DONE);
    }


    @Override
    public void changeOrderStatusWaitingForExpertToComeToYourPlace(Offer offer) {
       offer.getOrder().setStatusOrder(StatusOrder.WAITING_FOR_EXPERT_TO_COME_TO_YOUR_PLACE);
    }

    @Override
    public Collection<Offer> findAllByOrder(Customer customer) {
        return offerService.findAllByOrderOfCustomer(customer) ;
    }

}
