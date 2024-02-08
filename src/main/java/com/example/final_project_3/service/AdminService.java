package com.example.final_project_3.service;




import com.example.final_project_3.entity.*;
import com.example.final_project_3.dto.UserSearch;
import com.example.final_project_3.service.user.BaseUserService;

import java.util.Collection;
import java.util.List;


public interface AdminService extends BaseUserService<Admin> {
    void saveService(String serviceName);
    void saveSubService(String serviceName,SubService subService);
    void deleteExpertFromSubService(String subServiceName, Integer expertId);
    void saveExpertForSubService(String subServiceName, Integer expertId);
    Collection<SubService> ShowAllSubService();
    Collection<BasicService> showAllService();
    Collection<Expert> showAllExpert();
    void changeExpertStatus(Integer expertId);
    boolean existByServiceName(String serviceName);
    void editSubService(String subServiceName, double price, String description);
    List<BaseUser> search(UserSearch searchCriteria);

}
