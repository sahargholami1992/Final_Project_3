package com.example.final_project_3.service.impl;



import com.example.final_project_3.entity.*;
import com.example.final_project_3.entity.enumaration.ExpertStatus;
import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.entity.enumaration.Roll;
import com.example.final_project_3.exceptions.DoesNotMatchField;
import com.example.final_project_3.exceptions.DuplicateException;
import com.example.final_project_3.exceptions.NotFoundException;
import com.example.final_project_3.repository.AdminRepository;
import com.example.final_project_3.service.*;
import com.example.final_project_3.dto.UserSearch;

import com.example.final_project_3.service.user.BaseUserServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Transactional(readOnly = true)
@Service
public class AdminServiceImpl extends BaseUserServiceImpl<Admin, AdminRepository>
        implements AdminService {
    protected final SubServiceService subServiceService;
    protected final BasicServiceService basicServiceService;
    protected final ExpertService expertService;
    protected final CustomerService customerService;
    protected final UserService userService;
    private final Validator validator;

    public AdminServiceImpl(AdminRepository repository, SubServiceService subServiceService, BasicServiceService basicServiceService, ExpertService expertService, CustomerService customerService, UserService userService, Validator validator) {
        super(repository);
        this.subServiceService = subServiceService;
        this.basicServiceService = basicServiceService;
        this.expertService = expertService;
        this.customerService = customerService;
        this.userService = userService;
        this.validator = validator;
    }

    @PostConstruct
    @Transactional
    public void init() {
        if (repository.count() == 0) {
            Admin admin = new Admin();
            admin.setFirstName("admin");
            admin.setLastName("Admin");
            admin.setEmail("Admin@admin.com");
            admin.setPassword("Admin123");
            admin.setDateRegister(LocalDate.now());
            admin.setPermissions(Permissions.ACCEPTED);
            admin.setRoll(Roll.ADMIN);
            repository.save(admin);
        }
    }

    @Override
    @Transactional
    public void saveService(String serviceName) {
        if (basicServiceService.existByServiceName(serviceName))
            throw new DuplicateException(" this service existed ");
        BasicService basicService = new BasicService();
        basicService.setServiceName(serviceName);
        basicServiceService.saveOrUpdate(basicService);
    }

    @Override
    @Transactional
    public void saveSubService(String serviceName,SubService subService) {
        if (!basicServiceService.existByServiceName(serviceName)
                && subServiceService.existByName(subService.getSubServiceName()))
            throw new NotFoundException(" this service not exist or duplicate subService name ");
        BasicService basicService = basicServiceService.findByServiceName(serviceName);
        subService.setBasicService(basicService);
        subServiceService.saveOrUpdate(subService);
    }

    @Override
    @Transactional
    public void deleteExpertFromSubService(String subServiceName, Integer expertId) {
        if (!subServiceService.existByName(subServiceName)
                && !expertService.existById(expertId) )
            throw new NotFoundException(" Expert or SubService not found in the database   ");
        SubService subService = subServiceService.findBySubServiceName(subServiceName);
        Expert expert = expertService.findById(expertId);
        if (!subService.getExperts().contains(expert))throw new DoesNotMatchField("this expert not contains subService");
        subServiceService.deleteByEXPERT(subService, expert);
    }

    @Override
    @Transactional
    public void saveExpertForSubService(String subServiceName, Integer expertId) {
        if (!subServiceService.existByName(subServiceName)
                && !expertService.existById(expertId) )
            throw new NotFoundException(" Expert or SubService not found in the database");
        SubService subService = subServiceService.findBySubServiceName(subServiceName);
        Expert expert = expertService.findById(expertId);
        if (!expert.getExpertStatus().equals(ExpertStatus.ACCEPTED))throw new DoesNotMatchField("this expert status not accepted");
        subServiceService.saveExpert(subService, expert);
    }


    @Override
    public Collection<SubService> ShowAllSubService() {
        return subServiceService.loadAll();

    }

    @Override
    public Collection<BasicService> showAllService() {
        return basicServiceService.loadAll();
    }

    @Override
    public Collection<Expert> showAllExpert() {
        return expertService.loadAll();
    }

    @Override
    @Transactional
    public void changeExpertStatus(Integer expertId) {
        Expert expert = expertService.findById(expertId);
        expert.setExpertStatus(ExpertStatus.ACCEPTED);
        expert.setPermissions(Permissions.ACCEPTED);
        expertService.changeExpertStatus(expert);
    }

    @Override
    public boolean existByServiceName(String serviceName) {
        return basicServiceService.existByServiceName(serviceName);
    }

    @Override
    @Transactional
    public void editSubService(String subServiceName, double price, String description) {
        subServiceService.editSubService(subServiceName, price, description);
    }




    @Override
    public List<BaseUser> search(UserSearch search) {
        return userService.searchUsers(search);
    }
}
