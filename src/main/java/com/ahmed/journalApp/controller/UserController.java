package com.ahmed.journalApp.controller;
import com.ahmed.journalApp.entity.User;
import com.ahmed.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired

    @GetMapping
    public List<User> getAllUsers(){

        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @PutMapping
    public ResponseEntity<Object>  updateUser(@RequestBody User user){
       User userInDb=  userService.findByUsername(user.getUsername());
       if(userInDb != null){
           userInDb.setUsername(user.getUsername());
           userInDb.setPassword(user.getPassword());
           userService.saveEntry(userInDb);
       }
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    





}
