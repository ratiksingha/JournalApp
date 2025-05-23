package com.singha.journalApp.service;


import com.singha.journalApp.entity.User;

import com.singha.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public static  final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveEntry(User user){

        userRepository.save(user);
    }

    public void saveNewUser(User user){
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    if(user.getRoles()==null || user.getRoles().isEmpty()){
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }
    else {
        userRepository.save(user);
    }


    }


    public List<User> getAll(){
        //Check whether the user is admin or not
        //If the user is admin then return all users
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String userNameFromAuth=auth.getName();
        User user=userRepository.findByUserName(userNameFromAuth);
        if(user.getRoles().contains("ADMIN")){
            return userRepository.findAll();
        }
        else {
            return null;
        }


    }

    public Optional<User> findById(ObjectId id){

        return  userRepository.findById(id);
    }

    public void deleteByUsername(String userName){

        userRepository.deleteByUserName(userName);
    }

    public User findByUserName(String userName){

        return userRepository.findByUserName(userName);
    }
}
