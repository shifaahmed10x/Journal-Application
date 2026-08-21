package com.ahmed.journalApp.controller;
import com.ahmed.journalApp.entity.MyUserResponse;
import com.ahmed.journalApp.entity.User;
import com.ahmed.journalApp.entity.UserResponse;
import com.ahmed.journalApp.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<MyUserResponse> getMyUser(Authentication authentication){
        String loggedInUser = authentication.getName();
        User user = userService.findByUsername(loggedInUser);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MyUserResponse myuserResponse = new MyUserResponse(user.getUsername(),
                                                           user.getRoles(),
                                                           user.getJournalEntries());
        return new ResponseEntity<>(myuserResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid  @RequestBody User user ){
        if(userService.findByUsername(user.getUsername()) !=null){
            return new ResponseEntity<>("Username already exits",HttpStatus.CONFLICT);
        }
        userService.saveNewUser(user);
        return new ResponseEntity<>("User created Successfully",HttpStatus.OK);
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
