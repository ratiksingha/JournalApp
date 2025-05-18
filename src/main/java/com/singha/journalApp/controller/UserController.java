package com.singha.journalApp.controller;

import com.singha.journalApp.entity.User;

import com.singha.journalApp.repository.UserRepository;
import com.singha.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{myId}")
    public ResponseEntity<User> getUserByID(@PathVariable ObjectId myId){
        Optional<User> entryById=userService.findById(myId);
        return entryById.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    //Delete
    @DeleteMapping()
    public String deleteUserByUsername(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUsername(authentication.getName());
        return "Entry Deleted";
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
