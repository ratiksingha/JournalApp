package com.singha.journalApp.controller;

import com.singha.journalApp.entity.User;
import com.singha.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(){
        List<User> entries=userService.getAll();
        if(entries!=null && !entries.isEmpty()){
            return new  ResponseEntity<>(entries, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
