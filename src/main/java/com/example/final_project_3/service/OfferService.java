package com.example.final_project_3.service;



import com.example.final_project_3.entity.Customer;
import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Offer;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.service.dto.OfferDto;

import java.util.Collection;

public interface OfferService {
    void saveOffer(Expert expert, Order order, OfferDto dto);
    Collection<Offer> getOffersForOrder(Customer customer, String sortBy);
    Collection<Offer> findAllByOrderOfCustomer(Customer customer);

}
