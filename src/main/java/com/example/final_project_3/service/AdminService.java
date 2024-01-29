package com.example.final_project_3.service;




import com.example.final_project_3.entity.Admin;
import com.example.final_project_3.entity.BasicService;
import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.SubService;
import com.example.final_project_3.service.user.UserService;

import java.util.Collection;


public interface AdminService extends UserService<Admin> {
    void saveService(String serviceName);
    void saveSubService(String serviceName,SubService subService);
    void deleteExpertFromSubService(SubService subService, Expert expert);
    void saveExpertForSubService(SubService subService, Expert expert);
    void registerService(BasicService basicService);
    Collection<SubService> ShowAllSubService();
    Collection<BasicService> showAllService();
    Collection<Expert> showAllExpert();
    void changeExpertStatus(Expert expert);
    boolean existByServiceName(String serviceName);
    void editSubService(String subServiceName, double price, String description);
}
