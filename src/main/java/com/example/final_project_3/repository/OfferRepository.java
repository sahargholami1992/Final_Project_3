package com.example.final_project_3.repository;


import com.example.final_project_3.entity.Customer;
import com.example.final_project_3.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer> {
    @Query(value = "select o FROM Offer o where o.order.customer = :customer order by o.recommendedPrice")
    Collection<Offer> findAllByOrderOrderByRecommendedPrice(Customer customer);
    @Query(value = "select o FROM Offer o where o.order.customer = :customer order by o.expert.score")
    Collection<Offer> findAllByOrderOrderByExpertPrice(Customer customer);
    @Query(value = "select o from Offer o where o.order.customer = :customer")
    Collection<Offer> findAllByOrderOfCustomer(Customer customer);
}
