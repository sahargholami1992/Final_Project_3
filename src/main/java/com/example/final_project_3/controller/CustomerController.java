package com.example.final_project_3.controller;

import com.example.final_project_3.dto.*;
import com.example.final_project_3.entity.*;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.exceptions.NoMatchResultException;
import com.example.final_project_3.mapper.*;
import com.example.final_project_3.service.CustomerService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController {
    private final CustomerService customerService;
    private final Validator validator;

    @CrossOrigin
    @PostMapping("/payment")
    public ResponseEntity<String> payment(@RequestBody PaymentRequest request, Model model){
        customerService.processOnlinePayment(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@RequestBody CustomerRegisterDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<CustomerRegisterDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new NoMatchResultException("invalid regex format" + violations);
        }
        Customer customer = CustomersMapper.INSTANCE.convertToEntity(dto);
        Customer registerCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(
                CustomersMapper.INSTANCE.convertToDto(registerCustomer)
        );
    }

    @PutMapping("changePassword")
    public ResponseEntity<CustomerResponse> changePassword(@RequestBody ChangePasswordDto dto,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new NoMatchResultException("invalid regex format password");
        }
        Customer customer = customerService.changePassword(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(
                CustomersMapper.INSTANCE.convertToDto(customer)
        );
    }
    @GetMapping("/showAllBasicService")
    public ResponseEntity<Collection<BasicService>> showAllBasicService(){
        return ResponseEntity.ok(customerService.showAllService());
    }

    @GetMapping("/showAllSubServiceByService")
    public ResponseEntity<Collection<SubServiceResponse>> showAllSubServiceByService(@RequestParam String basicServiceName){
        Collection<SubService> subServices = customerService.showAllSubServiceByService(basicServiceName);
        return ResponseEntity.ok(
                SubServiceMapper.INSTANCE.convertCollectionsToDto(subServices)
        );
    }

    @PostMapping("/addOrder")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderDto dto){
        Order order = customerService.addOrder(dto);
        return ResponseEntity.ok(
                OrderMapper.INSTANCE.convertToResponse(order)
        );
    }
    @PutMapping("/changeOrderStatusToStarted")
    public ResponseEntity<Void> changeOrderStatusToStarted(@RequestParam  Integer offerId){
        customerService.changeOrderStatusToStarted(offerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/changeOrderStatus")
    public ResponseEntity<Void> changeOrderStatus(@RequestParam Integer offerId,@RequestParam StatusOrder statusOrder){
        customerService.changeOrderStatus(offerId,statusOrder);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @GetMapping("/getOffersForOrder")
    public ResponseEntity<Collection<OfferResponse>> getOffersForOrder(@RequestParam Integer customerId, @RequestParam String sortBy) {
        Collection<Offer> offers = customerService.getOffersForOrder(customerId,sortBy);
        return ResponseEntity.ok(
                OffersMapper.INSTANCE.offersToDto(offers)
        );
    }

    @GetMapping("/findAllByOrder")
    public ResponseEntity<Collection<OfferResponse>> findAllByOrder(@RequestParam Integer customerId){
        Collection<Offer> offers = customerService.findAllByOrder(customerId);
        return ResponseEntity.ok(
                OffersMapper.INSTANCE.offersToDto(offers)
        );
    }

    @PutMapping("/paymentFromCredit")
    public ResponseEntity<Void> paymentFromCredit(Integer offerId){
        customerService.paymentFromCredit(offerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/addReview")
    public ResponseEntity<Void> addReview(@RequestBody AddReviewDto dto){
        customerService.addReview(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
