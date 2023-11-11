package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.student;

@Controller
public class FirstController {
    @GetMapping("/")
    public String showinde() {
        return "index";
    }

    @PostMapping("/regiter")
    public String fun(@ModelAttribute student s) {
        System.out.println(s.toString());

        return "index";
    }
    // مساعد لإنشاء العناصر وإضافتها إلى الوثيقة

}
