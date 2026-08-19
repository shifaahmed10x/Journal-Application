package com.ahmed.journalApp.controller;

import com.ahmed.journalApp.entity.JournalEntry;
import com.ahmed.journalApp.entity.User;
import com.ahmed.journalApp.service.JournalEntryService;
import com.ahmed.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(Authentication authentication){
        String loggedInUsername = authentication.getName();

        User user = userService.findByUsername(loggedInUsername);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

      List<JournalEntry> all = user.getJournalEntries();

      return new ResponseEntity<>(all,HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,Authentication authentication){
        String loggedInUsername = authentication.getName();
        journalEntryService.saveEntry(myEntry,loggedInUsername);
        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId ,Authentication authentication){
        String loggedInUsername = authentication.getName();
        User user = userService.findByUsername(loggedInUsername);
        if(user ==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean ownsJournal = user.getJournalEntries()
                .stream()
                .anyMatch(entry-> entry.getId().equals(myId));
        if(!ownsJournal){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return journalEntryService.findById(myId)
                .map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id//{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, Authentication authentication){
        String loggedInUsername = authentication.getName();
        journalEntryService.deleteById(myId,loggedInUsername);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id//{id}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry,
            Authentication authentication){
        String loggedInUsername = authentication.getName();
        JournalEntry updatedEntry = journalEntryService.updateJournalEntry(
                id,
                newEntry,
                loggedInUsername
        );
        if (updatedEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedEntry,HttpStatus.NOT_FOUND);

    }
}
