package com.ahmed.journalApp.controller;

import com.ahmed.journalApp.entity.JournalEntry;
import com.ahmed.journalApp.entity.User;
import com.ahmed.journalApp.entity.UserResponse;
import com.ahmed.journalApp.service.JournalEntryService;
import com.ahmed.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService ;
    @Autowired
    JournalEntryService journalEntryService;
    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
       List<UserResponse> all =  userService.getAll()
               .stream()
               .map(user -> new UserResponse(
                       user.getUsername(),
                       user.getRoles()
               )).toList();
       if(all.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @GetMapping("/journals")
    public ResponseEntity<List<JournalEntry>> getAllJournals() {

        List<JournalEntry> all = journalEntryService.getAll();

        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
