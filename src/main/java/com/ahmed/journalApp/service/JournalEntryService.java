package com.ahmed.journalApp.service;

import com.ahmed.journalApp.entity.JournalEntry;
import com.ahmed.journalApp.entity.User;
import com.ahmed.journalApp.repo.JournalEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "User not found: " + username
                );
            }
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
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
        if (user == null){
            throw new RuntimeException("User not found");
        }
        boolean removed = user.getJournalEntries()
                        .removeIf(entry-> entry.getId().equals(id));
        if(!removed){
            throw new RuntimeException("Journal does not belong to this user");
        }
        userService.saveEntry(user);
        journalEntryRepo.deleteById(id);
    }

    public JournalEntry updateJournalEntry(
            ObjectId id,
            JournalEntry newEntry,
            String username) {

        User user = userService.findByUsername(username);

        if (user == null) {
            return null;
        }

        boolean ownsJournal = user.getJournalEntries()
                .stream()
                .anyMatch(entry -> entry.getId().equals(id));

        if (!ownsJournal) {
            return null;
        }

        JournalEntry oldEntry = journalEntryRepo.findById(id).orElse(null);

        if (oldEntry == null) {
            return null;
        }

        oldEntry.setTitle(newEntry.getTitle());
        oldEntry.setContent(newEntry.getContent());

        return journalEntryRepo.save(oldEntry);
    }

}
