package com.singha.journalApp.controller;

import com.singha.journalApp.entity.JournalEntry;
import com.singha.journalApp.entity.User;
import com.singha.journalApp.service.JournalEntryService;
import com.singha.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    //GET
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String userNameFromAuth=auth.getName();
        User user=userService.findByUserName(userNameFromAuth);
        List<JournalEntry> entries=user.getJournalEntries();
        if(entries!=null && !entries.isEmpty()){

            return new  ResponseEntity<>(entries,HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryByID(@PathVariable ObjectId myId){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String userNameFromAuth=auth.getName();
        User user=userService.findByUserName(userNameFromAuth);
        List<JournalEntry> resultEntry=user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).collect(Collectors.toList());
        if(resultEntry!=null && !resultEntry.isEmpty()){
            Optional<JournalEntry> entryById=journalEntryService.findById(myId);
            if(entryById.isPresent()){
                return new ResponseEntity<>(entryById.get(),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }

        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //POST
    @PostMapping("/create")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userNameFromAuth = auth.getName();
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, userNameFromAuth);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }
    //Delete

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryOfUserById(@PathVariable ObjectId myId){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        boolean result=journalEntryService.deleteByUserId(myId,userName);
       if(result){
           return new ResponseEntity<>(HttpStatus.OK);
       }
       else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
    //Update
    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String userNameFromAuth=auth.getName();
        User user=userService.findByUserName(userNameFromAuth);
        List<?> resultToFind=user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).collect(Collectors.toList());
        if(!resultToFind.isEmpty()){
            JournalEntry oldjournalEntry= journalEntryService.findById(myId).orElse(null);
            if(oldjournalEntry!=null){
                oldjournalEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle():oldjournalEntry.getTitle());
                oldjournalEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : oldjournalEntry.getContent());
                journalEntryService.saveEntry(oldjournalEntry,userNameFromAuth);
                return new ResponseEntity<>(oldjournalEntry,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

            }

}
