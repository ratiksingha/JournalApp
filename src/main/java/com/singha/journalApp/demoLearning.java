package com.singha.journalApp;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoLearning {

    @GetMapping("hello")
    public String sayHello(){
        return "Hello Hello HEHEHE";
    }
}
