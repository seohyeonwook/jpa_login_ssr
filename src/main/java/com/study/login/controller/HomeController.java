package com.study.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 기본 페이지 요청 메서드
    @GetMapping("/")
    public String index() {
        return "index"; // 요청이오면 templates 폴더의 index.html을 찾아감
    }


}
