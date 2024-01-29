package com.example.final_project_3.service.impl;


import com.example.final_project_3.repository.CommentRepository;
import com.example.final_project_3.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    protected final CommentRepository repository;

}
