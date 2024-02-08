package com.example.final_project_3.repository;


import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
   @Query("SELECT o FROM Order o JOIN o.subService s JOIN s.experts e WHERE e = :expert")
   List<Order> findOrderByForExpert(Expert expert);


}



