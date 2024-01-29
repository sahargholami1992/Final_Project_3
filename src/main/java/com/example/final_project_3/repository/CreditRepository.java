package com.example.final_project_3.repository;


import com.example.final_project_3.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CreditRepository extends JpaRepository<Credit,Integer> {

}
