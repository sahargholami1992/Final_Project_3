package com.example.final_project_3.service;


import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Offer;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.dto.OfferDto;
import com.example.final_project_3.dto.ReviewProjection;
import com.example.final_project_3.service.user.BaseUserService;

import java.util.Collection;
import java.util.List;

public interface ExpertService extends BaseUserService<Expert> {
    Expert registerExpert(Expert expert,String imagePath);

    void changeExpertStatus(Expert expert);
    Offer sendOffer(OfferDto dto);
    boolean saveImageToFile(Integer expertId);
    Collection<Order> getPendingOrdersForExpert(Integer expertId);
    Expert saveExpert(Expert expert);
    List<ReviewProjection> getReviewsForExpert(Integer expertId);

}
