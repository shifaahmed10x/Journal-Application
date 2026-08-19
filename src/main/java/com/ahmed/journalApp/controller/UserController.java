package com.ahmed.journalApp.controller;
import com.ahmed.journalApp.entity.User;
import com.ahmed.journalApp.service.UserService;
import org.springframework.security.core.Authentication;
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

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @PutMapping()
    public ResponseEntity<Object>  updateUser( @RequestBody User user , Authentication authentication) {

        String loggedInUsername = authentication.getName();

       User userInDb=  userService.findByUsername(loggedInUsername);
       if(userInDb != null){
           
           userService.updateUser(userInDb,user.getPassword());
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);

       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUserByUsername(Authentication authentication){
        String loggedInUsername = authentication.getName();
        userService.deleteByUsername(loggedInUsername);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    





}
