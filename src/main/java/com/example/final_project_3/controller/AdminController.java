package com.example.final_project_3.controller;

import com.example.final_project_3.dto.*;
import com.example.final_project_3.entity.BaseUser;
import com.example.final_project_3.entity.BasicService;
import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.SubService;
import com.example.final_project_3.exceptions.NoMatchResultException;
import com.example.final_project_3.mapper.ExpertsMapper;
import com.example.final_project_3.mapper.SubServiceMapper;
import com.example.final_project_3.mapper.UserMapper;
import com.example.final_project_3.service.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    protected final AdminService adminService;
    private final Validator validator;
    @PostMapping("/saveBasicService")
    public ResponseEntity<Void> saveBasicService(@RequestBody AddBasicServiceDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<AddBasicServiceDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new NoMatchResultException("service name regex is not correct");
        }
        adminService.saveService(dto.getServiceName());
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @PostMapping("/saveSubService")
    public ResponseEntity<Void> saveSubService(@RequestBody  SubServiceSaveDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<SubServiceSaveDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new NoMatchResultException("invalid regex format" + violations);
        }
        SubService subService= new SubService();
        subService.setSubServiceName(dto.getSubServiceName());
        subService.setBasePrice(dto.getBasePrice());
        subService.setDescription(dto.getDescription());
        adminService.saveSubService(dto.getServiceName(),subService);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteExpertOfSubService")
    public ResponseEntity<Void> deleteExpertFromSubService(@RequestParam String subServiceName,@RequestParam Integer expertId){
        adminService.deleteExpertFromSubService(subServiceName,expertId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/saveExpertForSubService")
    public ResponseEntity<Void> saveExpertForSubService(@RequestBody SaveExpertForSubService dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<SaveExpertForSubService>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new NoMatchResultException("subServiceName regex no correct");
        }
        adminService.saveExpertForSubService(dto.getSubServiceName(),dto.getExpertId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/showAllSubService")
    public ResponseEntity<Collection<SubServiceResponse>> showAllSubService(){
        Collection<SubService> subServices = adminService.ShowAllSubService();
        return ResponseEntity.ok(
                SubServiceMapper.INSTANCE.convertCollectionsToDto(subServices)
        );
    }

    @GetMapping("/showAllBasicService")
    public ResponseEntity<Collection<BasicService>> showAllBasicService(){
        return ResponseEntity.ok(adminService.showAllService());
    }
    @GetMapping("/showAllExpert")
    public ResponseEntity<Collection<ExpertResponseDto>> showAllExpert(){
        Collection<Expert> experts = adminService.showAllExpert();
        Collection<ExpertResponseDto> expertResponseDto = ExpertsMapper.INSTANCE.convertToDto(experts);
        return ResponseEntity.ok(
                expertResponseDto
        );
    }
    @PutMapping("/changeExpertStatus")
    public ResponseEntity<Void> changeExpertStatus(@RequestParam  Integer expertId){
        adminService.changeExpertStatus(expertId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @GetMapping("/existByServiceName")
    public ResponseEntity<ResultDTO<Boolean>> existByServiceName(@RequestParam String serviceName){
        return ResponseEntity.ok(
                new ResultDTO<>(
                        adminService.existByServiceName(serviceName)
                )
        );
    }

    @PutMapping("/editSubService")
    public ResponseEntity<Void> editSubService(@RequestBody @Valid EditSubServiceDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<EditSubServiceDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new NoMatchResultException("invalid regex format" + violations);
        }
        adminService.editSubService(dto.getSubServiceName(), dto.getPrice(),dto.getDescription());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PostMapping("/search")
    public ResponseEntity<List<SearchUserResponse>> search(@RequestBody UserSearch dto){
        List<BaseUser> users = adminService.search(dto);
        return ResponseEntity.ok(
                UserMapper.INSTANCE.convertToDto(users)
        );
    }


}
