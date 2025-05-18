package com.singha.journalApp.controller;

import com.singha.journalApp.entity.User;
import com.singha.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    //POST
    @PostMapping("/createUser")
    public String createUser(@RequestBody User user){
        userService.saveNewUser(user);
        return "User Created";
    }
}
