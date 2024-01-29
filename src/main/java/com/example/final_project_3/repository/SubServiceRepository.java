package com.example.final_project_3.repository;


import com.example.final_project_3.entity.BasicService;
import com.example.final_project_3.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface SubServiceRepository extends JpaRepository<SubService,Integer> {

    Collection<SubService> findByBasicService(BasicService basicService);

    boolean existsBySubServiceName(String subServiceName);
    Optional<SubService> findBySubServiceName(String subServiceName);

}
