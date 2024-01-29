package com.example.final_project_3.service;


import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.service.dto.ExpertRegisterDto;
import com.example.final_project_3.service.dto.OfferDto;
import com.example.final_project_3.service.user.UserService;

public interface ExpertService extends UserService<Expert> {
    Expert registerExpert(ExpertRegisterDto dto);

    void changeExpertStatus(Expert expert);
    void sendOffer(Expert expert, Order order, OfferDto dto);
    byte[] readsImage(String imageName);
    boolean saveImageToFile(byte[] imageData, String outputPath);
    Expert saveExpert(Expert expert);

}
