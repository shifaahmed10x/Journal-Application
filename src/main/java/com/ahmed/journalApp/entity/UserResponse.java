package com.ahmed.journalApp.entity;

import lombok.Data;

import java.util.List;
@Data
public class UserResponse {

    private String username;
    private List<String> roles;

    public UserResponse(String username,List<String>roles){
        this.username = username;
        this.roles = roles;
    }
}