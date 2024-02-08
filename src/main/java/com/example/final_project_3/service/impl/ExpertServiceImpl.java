package com.example.final_project_3.service.impl;


import com.example.final_project_3.entity.*;
import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.exceptions.DuplicateException;
import com.example.final_project_3.exceptions.NotFoundException;
import com.example.final_project_3.repository.ExpertRepository;
import com.example.final_project_3.service.*;
import com.example.final_project_3.dto.OfferDto;
import com.example.final_project_3.dto.ReviewProjection;
import com.example.final_project_3.service.user.BaseUserServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExpertServiceImpl extends BaseUserServiceImpl<Expert, ExpertRepository>
                              implements ExpertService {
    protected final OfferService offerService;
    protected final ReviewService reviewService;
    protected final CreditService creditService;
    protected final OrderService orderService;
    public ExpertServiceImpl(ExpertRepository repository, OfferService offerService, ReviewService reviewService, CreditService creditService, OrderService orderService) {
        super(repository);
        this.offerService=offerService;
        this.reviewService=reviewService;
        this.creditService = creditService;
        this.orderService = orderService;
    }
    @Transactional
    @Override
    public Expert registerExpert(Expert expert,String imagePath) {
       if (existByEmail(expert.getEmail()))
           throw new DuplicateException("this email existed");
       expert.setProfileImage(readsImage(imagePath));
       expert.setPermissions(Permissions.WAITING);
       Credit credit = new Credit();
       credit.setBalance(0);
        Credit saveCredit = creditService.saveCredit(credit);
        expert.setCredit(saveCredit);
        return repository.save(expert);
    }
    private static byte[] readsImage(String imageName)  {
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
    @Transactional
    @Override
    public void changeExpertStatus(Expert expert) {
        repository.save(expert);
    }
    @Transactional
    @Override
    public Offer sendOffer(OfferDto dto) {
        return offerService.saveOffer(dto);
    }



    public boolean saveImageToFile(Integer expertId) {
        if (!existById(expertId))throw new NotFoundException("this expert id is null");
        Expert expert = findById(expertId);
        try (OutputStream outputStream = new FileOutputStream("output.jpg")) {
            outputStream.write(expert.getProfileImage());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Collection<Order> getPendingOrdersForExpert(Integer expertId) {
        if (!existById(expertId))throw new NotFoundException("this expert id is null");
        Expert expert = findById(expertId);
        return orderService.getPendingOrdersForExpert(expert);
    }

    @Override
    public Expert saveExpert(Expert expert) {
        return repository.save(expert);
    }

    @Override
    public List<ReviewProjection> getReviewsForExpert(Integer expertId) {
        return reviewService.findByExpertId(expertId);
    }

}
