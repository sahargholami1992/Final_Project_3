package com.example.final_project_3.service.impl;




import com.example.final_project_3.entity.*;
import com.example.final_project_3.entity.enumaration.ExpertStatus;
import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.exceptions.*;
import com.example.final_project_3.repository.CustomerRepository;
import com.example.final_project_3.service.*;
import com.example.final_project_3.dto.AddReviewDto;
import com.example.final_project_3.dto.OrderDto;
import com.example.final_project_3.dto.PaymentRequest;
import com.example.final_project_3.service.user.BaseUserServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl extends BaseUserServiceImpl<Customer, CustomerRepository>
                              implements CustomerService {
    protected final SubServiceService subServiceService;
    protected final BasicServiceService basicServiceService;
    protected final OrderService orderService;
    protected final OfferService offerService;
    protected final CreditService creditService;
    protected final PaymentService paymentService;
    protected final ReviewService reviewService;
    protected final ExpertService expertService;
    public CustomerServiceImpl(CustomerRepository repository, SubServiceService subServiceService, BasicServiceService basicServiceService, OrderService orderService, OfferService offerService, CreditService creditService, PaymentService paymentService, ReviewService reviewService, ExpertService expertService) {
        super(repository);
        this.subServiceService  =subServiceService;
        this.basicServiceService = basicServiceService;
        this.orderService=orderService;
        this.offerService=offerService;
        this.creditService=creditService;
        this.paymentService=paymentService;
        this.reviewService = reviewService;
        this.expertService = expertService;
    }
    @Transactional
    @Override
    public Customer registerCustomer(Customer customer) {
        if (existByEmail(customer.getEmail()))
            throw new DuplicateException("this email existed");
        customer.setPermissions(Permissions.ACCEPTED);
        Credit credit = new Credit();
        credit.setBalance(0);
        Credit saveCredit = creditService.saveCredit(credit);
        customer.setCredit(saveCredit);
        return repository.save(customer);
    }

    @Override
    public Collection<BasicService> showAllService() {
        return basicServiceService.loadAll();
    }

    @Override
    public Collection<SubService> showAllSubServiceByService(String basicServiceName) {
        if (!basicServiceService.existByServiceName(basicServiceName))
            throw new NotFoundException("this serviceName not found");
        BasicService basicService = basicServiceService.findByServiceName(basicServiceName);
        return subServiceService.findByService(basicService);
    }

    @Transactional
    @Override
    public Order addOrder(OrderDto dto) {
        if (!existById(dto.getCustomerId())
                && !subServiceService.existByName(dto.getSubServiceName()))
            throw new NotFoundException("customer or subService is null");
        Customer customer = findById(dto.getCustomerId());
        SubService subService = subServiceService.findBySubServiceName(dto.getSubServiceName());
        return orderService.addOrder(dto,customer,subService);
    }

    @Transactional
    @Override
    public void changeOrderStatusToStarted(Integer offerId) {
        if (!offerService.existById(offerId))
            throw new NotFoundException("this offer id not found");
        Offer offer = offerService.findById(offerId);
        if (!offer.getSuggestedTimeToStartWork().isAfter(LocalDate.now())) throw new DoesNotMatchField("TIME IS BEFORE NOW");
        orderService.changeOrderStatus(offer.getOrder(), StatusOrder.STARTED);
    }

    @Transactional
    @Override
    public void changeOrderStatus(Integer offerId,StatusOrder statusOrder) {
        if (!offerService.existById(offerId))
            throw new NotFoundException("this offer id not found");
        Offer offer = offerService.findById(offerId);
        orderService.changeOrderStatus(offer.getOrder(),statusOrder);
    }


    @Override
    public Collection<Offer> findAllByOrder(Integer customerId) {
        if (!repository.existsById(customerId))
            throw new NotFoundException("this customer id not found");
        Customer customer = findById(customerId);
        return offerService.findAllByOrderOfCustomer(customer) ;
    }

    @Override
    public Collection<Offer> getOffersForOrder(Integer customerId,String sortBy) {
        if (!repository.existsById(customerId))
            throw new NotFoundException("this customer id not found");
        Customer customer = findById(customerId);
        return offerService.getOffersForOrder(customer,sortBy);
    }

    @Transactional
    @Override
    public void paymentFromCredit(Integer offerId) {
        if (!offerService.existById(offerId))
            throw new NotFoundException("this offer id not found");
        Offer offer = offerService.findById(offerId);
        Credit customerCredit = offer.getOrder().getCustomer().getCredit();
        Order order = offer.getOrder();
        if (!order.getStatusOrder().equals(StatusOrder.DONE)
                || customerCredit.getBalance() <offer.getRecommendedPrice())
            throw new InsufficientCreditException("Insufficient credit balance");
        updateCredits(offer,customerCredit,order);
        checkTimeToReduceScore(offer);
    }
    @Transactional
    public void updateCredits(Offer offer,Credit customerCredit,Order order) {
        customerCredit.setBalance(customerCredit.getBalance()- offer.getRecommendedPrice());
        creditService.withdraw(customerCredit);
        order.setStatusOrder(StatusOrder.PAID);
        orderService.update(order);
        Credit expertCredit = offer.getExpert().getCredit();
        expertCredit.setBalance(offer.getRecommendedPrice()*0.7);
        creditService.saveCredit(expertCredit);
        checkTimeToReduceScore(offer);
    }

    @Transactional
    @Override
    public void processOnlinePayment(PaymentRequest paymentRequest) {
//        paymentService.paymentDone(paymentRequest);
        Offer offer = offerService.findById(paymentRequest.getOfferId());
        Order order = offer.getOrder();
        order.setStatusOrder(StatusOrder.PAID);
        orderService.update(order);
        Credit expertCredit = offer.getExpert().getCredit();
        expertCredit.setBalance(offer.getRecommendedPrice()*0.7);
        creditService.saveCredit(expertCredit);
    }

    @Transactional
    @Override
    public void addReview(AddReviewDto dto) {
        if (!offerService.existById(dto.getOfferId()))
            throw new NotFoundException("THIS OFFER NOT FOUND");
        Offer offer = offerService.findById(dto.getOfferId());
        Expert expert = offer.getExpert();
        Order order = offer.getOrder();
        Review review = new Review();
        review.setScore(dto.getScore());
        review.setComment(dto.getComment());
        review.setExpert(expert);
        if (!order.getStatusOrder().equals(StatusOrder.DONE) ||
                review.getScore()>5 || review.getScore()<1)
            throw new DoesNotMatchField("this score in out of bound or order not done");
        Review saveReview = reviewService.save(review);
        order.setReview(saveReview);
        orderService.update(order);
        expert.setScore(review.getScore());
        expertService.saveExpert(expert);
    }
    @Transactional
    public void checkTimeToReduceScore(Offer offer) {
        int timeToDone = offer.getSuggestedTimeToStartWork().atTime(2, 12).getHour() + offer.getDurationOfWork();
        int hours = ZonedDateTime.now().getHour() - timeToDone;
        Expert expert = offer.getExpert();
        for (int i = 0; i < hours; i++) {
            expert.setScore(expert.getScore() - 1);
            expertService.saveExpert(expert);
        }
        if (expert.getScore() < 0) {
            expert.setPermissions(Permissions.WAITING);
            expert.setExpertStatus(ExpertStatus.AWAITING_CONFIRMATION);
            expertService.saveExpert(expert);
        }
    }


}
