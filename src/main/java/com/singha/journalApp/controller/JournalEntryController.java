package com.singha.journalApp.controller;

import com.singha.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries =new HashMap<>();

    @GetMapping("/getAllEntries")
    public List<JournalEntry> getAll(){

        return new ArrayList<>(journalEntries.values());
    }
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){

        journalEntries.put(myEntry.getId(),myEntry);
    return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryByID(@PathVariable Long myId){
        return journalEntries.get(myId);
    }


    //Delete
    @DeleteMapping("id/{myId}")
    public String deleteJournalEntryById(@PathVariable Long myId){
        journalEntries.remove(myId);
        return "Entry deleted";
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
        return journalEntries.put(myId,myEntry);
    }

}
