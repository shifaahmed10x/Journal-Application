package com.ahmed.journalApp.service;

import com.ahmed.journalApp.entity.JournalEntry;
import com.ahmed.journalApp.entity.User;
import com.ahmed.journalApp.repo.JournalEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry,String username){
        try{
            User user = userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
           // log.error("Exception ",e);
            System.out.println(e);
            throw new RuntimeException("An error occuring while saving the entry",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        try{
            journalEntryRepo.save(journalEntry);
        } catch (Exception e) {
            log.error("Exception ",e);
        }
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    public  void deleteById(ObjectId id,String username){
        User user = userService.findByUsername(username);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepo.deleteById(id);
    }
}
