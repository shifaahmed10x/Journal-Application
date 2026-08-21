package com.ahmed.journalApp.entity;

import lombok.Data;

import java.util.List;

@Data
public class MyUserResponse {
    private String username ;
    private List<String> roles;
    private List<JournalEntry> journalEntries;

    public MyUserResponse(String username,List<String> roles,List<JournalEntry> journalEntries){
        this.username = username;
        this.roles = roles;
        this.journalEntries = journalEntries;
    }
}
