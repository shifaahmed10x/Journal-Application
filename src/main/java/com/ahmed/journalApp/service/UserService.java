package com.ahmed.journalApp.service;

import com.ahmed.journalApp.entity.User;
import com.ahmed.journalApp.repo.JournalEntryRepo;
import com.ahmed.journalApp.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    public void saveEntry(User user){
            userRepo.save(user);
    }

    public boolean saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepo.save(user);
            return true;
        } catch (Exception e){
            log.error("Error occured for {} and reason : {}" ,user.getUsername(),e.getCause());
            log.info("Info  occured for {} and reason : {}" ,user.getUsername());
            log.debug("Debug  occured for {} and reason : {}" ,user.getUsername());
            log.trace("Trace occured for {} and reason : {}" ,user.getUsername());

            return  false;
        }

    }
    public void saveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("ADMIN"));
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

    public void updateUser(User userInDB , String newPassword){
        userInDB.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(userInDB);
    }

    public void deleteByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if(user == null){
            return;
        }
        user.getJournalEntries().forEach(journalEntry -> {
         journalEntryRepo.deleteById(journalEntry.getId());
        });
        userRepo.delete(user);
    }
}
