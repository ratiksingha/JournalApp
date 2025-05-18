package com.singha.journalApp;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
public class demoLearning {

//    @GetMapping("hello")
//    public String sayHello(){
//        return "Hello Hello HEHEHE";
//    }


    public static void main(String[] args) {
        Function<Integer, Integer> doubleIt = x -> 2 * x;
        System.out.println(doubleIt.apply(200));

    }
}
