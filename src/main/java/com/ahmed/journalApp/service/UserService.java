package com.ahmed.journalApp.service;

import com.ahmed.journalApp.entity.User;
import com.ahmed.journalApp.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public void saveEntry(User user){
            userRepo.save(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepo.findById(id);
    }

    public  void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }

    public  User findByUsername(String username){
        return userRepo.findByUsername(username);
    }
}
