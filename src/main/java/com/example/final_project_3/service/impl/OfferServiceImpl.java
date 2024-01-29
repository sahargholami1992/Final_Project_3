package com.example.final_project_3.service.impl;


import com.example.final_project_3.entity.Customer;
import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Offer;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.entity.enumaration.StatusOrder;
import com.example.final_project_3.repository.OfferRepository;
import com.example.final_project_3.service.OfferService;
import com.example.final_project_3.service.OrderService;
import com.example.final_project_3.service.dto.OfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    protected final OfferRepository repository;
    protected final OrderService orderService;

    @Transactional
    @Override
    public void saveOffer(Expert expert, Order order, OfferDto dto) {
        Objects.requireNonNull(order, "Order cannot be null");
        if (!order.getStatusOrder().equals(StatusOrder.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS) && !order.getStatusOrder().equals(StatusOrder.WAITING_FOR_EXPERT_SELECTION)) {
            throw new IllegalStateException("Cannot send proposal for orders in the current status");
        }
        if (dto.getRecommendedPrice() > order.getSubService().getBasePrice() && dto.getSuggestedTimeToStartWork().isAfter(LocalDate.now())) {
            Offer offer = new Offer();
            offer.setDurationOfWork(dto.getDurationOfWork());
            offer.setSuggestedTimeToStartWork(dto.getSuggestedTimeToStartWork());
            offer.setRecommendedPrice(dto.getRecommendedPrice());
            offer.setDateRegisterOffer(dto.getDateRegisterOffer());
            offer.setOrder(order);
            offer.setExpert(expert);
            repository.save(offer);
            order.setStatusOrder(StatusOrder.WAITING_FOR_EXPERT_SELECTION);
            orderService.UpdateStatus(order);
        } else throw new IllegalArgumentException("Invalid proposal conditions ");
    }


    public Collection<Offer> getOffersForOrder(Customer customer, String sortBy) {
        Collection<Offer> offers = new ArrayList<>();
        switch (sortBy) {
            case "price" -> offers =repository.findAllByOrderOrderByRecommendedPrice(customer);

            case "rating" -> offers = repository.findAllByOrderOrderByExpertPrice(customer);

        }
        return offers;
    }

    @Override
    public Collection<Offer> findAllByOrderOfCustomer(Customer customer) {
        return repository.findAllByOrderOfCustomer(customer);
    }
}
