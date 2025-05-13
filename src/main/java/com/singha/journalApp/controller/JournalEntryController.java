package com.singha.journalApp.controller;

import com.singha.journalApp.entity.JournalEntry;
import com.singha.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;



    @GetMapping("/getAll")
    public List<JournalEntry> getAll(){

        return journalEntryService.getAll();
    }

    @PostMapping
    public String createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);

    return "Entry Created";
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryByID(@PathVariable ObjectId myId){
        return journalEntryService.findById(myId).orElse(null);
    }


    //Delete
    @DeleteMapping("id/{myId}")
    public String deleteJournalEntryById(@PathVariable ObjectId myId){

        journalEntryService.deleteById(myId);
        return "Entry Deleted";
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry oldjournalEntry= journalEntryService.findById(myId).orElse(null);
        if(oldjournalEntry!=null){
            oldjournalEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle():oldjournalEntry.getTitle());
            oldjournalEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : oldjournalEntry.getContent());

        }
        journalEntryService.saveEntry(
                oldjournalEntry
        );
        return oldjournalEntry;
    }

}
