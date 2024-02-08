package com.example.final_project_3.repository;


import com.example.final_project_3.entity.Review;
import com.example.final_project_3.dto.ReviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    @Query(value = "select new com.example.final_project_3.dto.ReviewProjection(r.score) from Review r where r.expert.id = :expert_id")
    List<ReviewProjection> findScoreByExpertId(Integer expert_id);
}
