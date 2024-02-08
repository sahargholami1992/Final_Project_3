package com.example.final_project_3.service.impl;


import com.example.final_project_3.entity.Review;
import com.example.final_project_3.repository.ReviewRepository;
import com.example.final_project_3.service.ReviewService;
import com.example.final_project_3.dto.ReviewProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    protected final ReviewRepository repository;

    @Override
    public List<ReviewProjection> findByExpertId(Integer expertId) {
        return repository.findScoreByExpertId(expertId);
    }

    @Transactional
    @Override
    public Review save(Review review) {
        return repository.save(review);
    }
}
