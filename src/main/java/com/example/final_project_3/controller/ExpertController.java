package com.example.final_project_3.controller;

import com.example.final_project_3.dto.*;
import com.example.final_project_3.entity.*;
import com.example.final_project_3.exceptions.NoMatchResultException;
import com.example.final_project_3.mapper.ExpertsMapper;
import com.example.final_project_3.mapper.OrderMapper;
import com.example.final_project_3.service.ExpertService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/expert")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertService expertService;
    private final Validator validator;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody ExpertRegisterDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<CustomerRegisterDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new NoMatchResultException("invalid regex format" + violations);
        }
        Expert expert = ExpertsMapper.INSTANCE.convertToEntity(dto);
        expertService.registerExpert(expert,dto.getImagePath());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("saveImageToFile")
    public ResponseEntity<ResultDTO<Boolean>> saveImageToFile(Integer expertId){
        return ResponseEntity.ok(
                new ResultDTO<>(
                        expertService.saveImageToFile(expertId)
                )
        );
    }
    @PutMapping("changePassword")
    public ResponseEntity<ExpertResponseDto> changePassword(@RequestBody ChangePasswordDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new NoMatchResultException("invalid regex format password");
        }
        Expert expert = expertService.changePassword(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(
                ExpertsMapper.INSTANCE.convertToResponse(expert)
        );
    }

    @PostMapping("/sendOffer")
    public ResponseEntity<Void> sendOffer(@RequestBody OfferDto dto,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<OfferDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new NoMatchResultException("invalid regex format" + violations);
        }
        expertService.sendOffer(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getReviewsForExpert")
    public ResponseEntity<List<ReviewProjection>> getReviewsForExpert(@RequestParam Integer expertId){
        return ResponseEntity.ok(expertService.getReviewsForExpert(expertId));
    }

    @GetMapping("/getPendingOrdersForExpert")
    public ResponseEntity<Collection<OrderResponse>> getPendingOrdersForExpert(@RequestParam Integer expertId){
        Collection<Order> orders = expertService.getPendingOrdersForExpert(expertId);
        return ResponseEntity.ok(
                OrderMapper.INSTANCE.convertToCollectionDto(orders)
        );
    }
}
