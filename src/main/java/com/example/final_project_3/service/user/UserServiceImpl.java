package com.example.final_project_3.service.user;



import com.example.final_project_3.entity.User;
import com.example.final_project_3.repository.user.UserRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl<T extends User,R extends UserRepository<T>> implements UserService<T>{

    protected final R repository;

    @Override
    public T changePassword(String email, String newPassword) {
        T user = repository.findByEmail(email).
                orElseThrow(() -> new NullPointerException("userName or password is wrong"));
        user.setPassword(newPassword);
        return repository.save(user);
    }

    @Override
    public boolean existByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public T logIn(String email, String password) {
        T user = repository.findByEmail(email).
                orElseThrow(() -> new NullPointerException("userName or password is wrong"));

        if (password.equals(user.getPassword())){
            return user;
        }
        throw new NoResultException("userName or password is wrong");

    }

    @Override
    public T findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new NullPointerException("this user not found"));
    }

    @Override
    public Collection<T> loadAll() {
        return repository.findAll();
    }
}
