package com.example.final_project_3.service;


import com.example.final_project_3.entity.Review;
import com.example.final_project_3.dto.ReviewProjection;

import java.util.List;

public interface ReviewService {


    List<ReviewProjection> findByExpertId(Integer expertId);

    Review save(Review review);
}
