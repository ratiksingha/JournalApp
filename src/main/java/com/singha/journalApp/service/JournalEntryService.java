package com.singha.journalApp.service;


import com.singha.journalApp.entity.JournalEntry;
import com.singha.journalApp.entity.User;
import com.singha.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;



@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry ,String userName){
        User user=userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved=journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return  journalEntryRepository.findById(id);
    }

    //DELETE
    public void deleteById(ObjectId id ){
        journalEntryRepository.deleteById(id);
    }

    public void deleteByUserId(ObjectId id , String userName){
        User user=userService.findByUserName(userName);
        user.getJournalEntries().removeIf(
                x -> x.getId().equals(id)
        );
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}
