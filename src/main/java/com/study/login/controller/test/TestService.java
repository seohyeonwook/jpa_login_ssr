package com.study.login.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    public List<Member> getAllMembers() {
        return testRepository.findAll();
    }
}
