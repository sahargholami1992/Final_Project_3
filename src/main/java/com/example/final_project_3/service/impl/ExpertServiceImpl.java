package com.example.final_project_3.service.impl;


import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.Order;
import com.example.final_project_3.entity.enumaration.ExpertStatus;
import com.example.final_project_3.repository.ExpertRepository;
import com.example.final_project_3.service.ExpertService;
import com.example.final_project_3.service.OfferService;
import com.example.final_project_3.service.dto.ExpertRegisterDto;
import com.example.final_project_3.service.dto.OfferDto;
import com.example.final_project_3.service.user.UserServiceImpl;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;

@Service
@Transactional(readOnly = true)
public class ExpertServiceImpl extends UserServiceImpl<Expert, ExpertRepository>
                              implements ExpertService {
    protected final OfferService offerService;
    public ExpertServiceImpl(ExpertRepository repository, OfferService offerService) {
        super(repository);
        this.offerService=offerService;
    }
    @Transactional
    @Override
    public Expert registerExpert(ExpertRegisterDto dto) {
        Expert expert = new Expert();
       expert.setFirstName(dto.getFirstName());
       expert.setLastName(dto.getLastName());
       expert.setEmail(dto.getEmail());
       expert.setPassword(dto.getPassword());
       expert.setDateRegister(dto.getDateRegister());
       expert.setRoll(dto.getRoll());
       expert.setPermissions(dto.getPermission());
       expert.setExpertStatus(dto.getExpertStatus());
       expert.setScore(dto.getScore());
       expert.setProfileImage(dto.getProfileImage());
       repository.save(expert);
        return expert;
    }
    @Transactional
    @Override
    public void changeExpertStatus(Expert expert) {
        repository.save(expert);
    }

    @Override
    public void sendOffer(Expert expert, Order order, OfferDto dto) {
        offerService.saveOffer(expert,order,dto);
    }

    @Override
    public Expert logIn(String email, String password) {

    Expert user = repository.findByEmail(email).
            orElseThrow(() -> new NoResultException("userName or password is wrong"));

    if (password.equals(user.getPassword()) && !user.getExpertStatus().equals(ExpertStatus.AWAITING_CONFIRMATION)) {
        return user;
    }else throw new RuntimeException("userName or password is wrong or you are not accepted yet");

}
    public byte[] readsImage(String imageName)  {
        InputStream inputStream = ExpertServiceImpl.class.getClassLoader().getResourceAsStream(imageName);
        if (inputStream != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    if ((bytesRead = inputStream.read(buffer)) == -1) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }else{
            return null;
        }
    }
    public boolean saveImageToFile(byte[] imageData, String outputPath) {
        try (OutputStream outputStream = new FileOutputStream(outputPath)) {
            outputStream.write(imageData);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Expert saveExpert(Expert expert) {
        return repository.save(expert);
    }

}
