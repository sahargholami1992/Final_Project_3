package com.example.final_project_3.service.impl;



import com.example.final_project_3.entity.Admin;
import com.example.final_project_3.entity.BasicService;
import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.SubService;
import com.example.final_project_3.entity.enumaration.ExpertStatus;
import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.entity.enumaration.Roll;
import com.example.final_project_3.repository.AdminRepository;
import com.example.final_project_3.service.AdminService;
import com.example.final_project_3.service.BasicServiceService;
import com.example.final_project_3.service.ExpertService;
import com.example.final_project_3.service.SubServiceService;
import com.example.final_project_3.service.user.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Transactional(readOnly = true)
@Service
public class AdminServiceImpl extends UserServiceImpl<Admin, AdminRepository>
        implements AdminService {
    protected final SubServiceService subServiceService;
    protected final BasicServiceService basicServiceService;
    protected final ExpertService expertService;

    public AdminServiceImpl(AdminRepository repository, SubServiceService subServiceService, BasicServiceService basicServiceService, ExpertService expertService) {
        super(repository);
        this.subServiceService = subServiceService;
        this.basicServiceService = basicServiceService;
        this.expertService = expertService;
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
        if (!basicServiceService.existByServiceName(serviceName)) {
            BasicService basicService = new BasicService();
            basicService.setServiceName(serviceName);
            basicServiceService.saveOrUpdate(basicService);
        } else throw new IllegalArgumentException(" this service existed ");
    }

    @Override
    @Transactional
    public void saveSubService(String serviceName, SubService subService) {
        if (basicServiceService.existByServiceName(serviceName) && !subServiceService.existByName(subService.getSubServiceName())) {
            BasicService basicService = basicServiceService.findByServiceName(serviceName);
            subService.setBasicService(basicService);
            subServiceService.saveOrUpdate(subService);
        } else throw new IllegalArgumentException(" this service not exist or duplicate subService name ");
    }

    @Override
    @Transactional
    public void deleteExpertFromSubService(SubService subService, Expert expert) {
        if (subService != null && expert != null && expert.getExpertStatus().equals(ExpertStatus.ACCEPTED) && subService.getExperts().contains(expert)) {
            subServiceService.deleteByEXPERT(subService, expert);
        } else throw new NullPointerException(" Expert or SubService not found in the database or ExpertStatus is not equals ACCEPTED  ");

    }

    @Override
    @Transactional
    public void saveExpertForSubService(SubService subService, Expert expert) {
        if (subService != null && expert != null && expert.getExpertStatus().equals(ExpertStatus.ACCEPTED)) {
            subServiceService.saveExpert(subService, expert);
        } else
            throw new NullPointerException(" Expert or SubService not found in the database or ExpertStatus is not equals ACCEPTED  ");

    }

    @Override
    @Transactional
    public void registerService(BasicService basicService) {
        basicServiceService.saveOrUpdate(basicService);
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
    public void changeExpertStatus(Expert expert) {
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
}
