package com.example.final_project_3.service.impl;



import com.example.final_project_3.entity.BasicService;
import com.example.final_project_3.repository.BasicServiceRepository;
import com.example.final_project_3.service.BasicServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BasicServiceServiceImpl implements BasicServiceService {
    protected final BasicServiceRepository repository;
    @Override
    public boolean existByServiceName(String serviceName) {
        return repository.existsByServiceName(serviceName);
    }

    @Override
    public BasicService findByServiceName(String serviceName) {
        return repository.findByServiceName(serviceName).orElseThrow(() -> new NullPointerException("Basic service is null"));
    }


    @Override
    public Collection<BasicService> loadAll() {
        return repository.findAll();
    }
    @Transactional
    @Override
    public void saveOrUpdate(BasicService basicService) {
        repository.save(basicService);
    }


}

