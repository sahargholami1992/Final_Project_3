package com.example.final_project_3.repository.user;

import com.example.final_project_3.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
@NoRepositoryBean
public interface BaseUserRepository<T extends BaseUser> extends JpaRepository<T,Integer> , JpaSpecificationExecutor<T> {

    boolean existsByEmail(String email);
    Optional<T> findByEmail(String email);




}
