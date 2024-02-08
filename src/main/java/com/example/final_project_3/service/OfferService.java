package com.example.final_project_3.service;



import com.example.final_project_3.entity.Customer;
import com.example.final_project_3.entity.Offer;
import com.example.final_project_3.dto.OfferDto;

import java.util.Collection;

public interface OfferService {
    Offer saveOffer(OfferDto dto);
    Collection<Offer> getOffersForOrder(Customer customer,String sortBy);
    Collection<Offer> findAllByOrderOfCustomer(Customer customer);

    boolean existById(Integer offerId);

    Offer findById(Integer offerId);
}
