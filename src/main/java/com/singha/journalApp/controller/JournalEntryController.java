package com.singha.journalApp.controller;

import com.singha.journalApp.entity.JournalEntry;
import com.singha.journalApp.entity.User;
import com.singha.journalApp.service.JournalEntryService;
import com.singha.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;



    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries(@PathVariable String userName){
        User user=userService.findByUserName(userName);
        List<JournalEntry> entries=user.getJournalEntries();
        if(entries!=null && !entries.isEmpty()){

            return new  ResponseEntity<>(entries,HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

    }

    @PostMapping("/{userName}")
    public String createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry,userName);

    return "Entry Created";
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryByID(@PathVariable ObjectId myId){
        Optional<JournalEntry> entryById=journalEntryService.findById(myId);
        return entryById.map((entry)-> new ResponseEntity<>(entry,HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    //Delete
    @DeleteMapping("id/{myId}")
    public String deleteJournalEntryById(@PathVariable ObjectId myId){

        journalEntryService.deleteById(myId);
        return "Entry Deleted";
    }

    @DeleteMapping("/{userName}/{myId}")
    public String deleteJournalEntryOfUserById(@PathVariable ObjectId myId, @PathVariable String userName){

        journalEntryService.deleteByUserId(myId,userName);
        return "Entry Deleted";
    }

//    @PutMapping("id/{myId}")
//    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
//        JournalEntry oldjournalEntry= journalEntryService.findById(myId).orElse(null);
//        if(oldjournalEntry!=null){
//            oldjournalEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle():oldjournalEntry.getTitle());
//            oldjournalEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : oldjournalEntry.getContent());
//
//        }
//        journalEntryService.saveEntry(
//                oldjournalEntry
//        );
//        return oldjournalEntry;
//    }

}
