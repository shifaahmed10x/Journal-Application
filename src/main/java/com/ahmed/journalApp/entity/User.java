package com.ahmed.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")
@Data
public class User {
    @Indexed(unique = true)// indexed - fast searching . unique = contain unique username
    @NotBlank
    private String username;
    @NonNull
    private String password;
    @Id
    private ObjectId id;
    @DBRef // "Don't store the whole JournalEntry object here. Store only references (IDs) to those journal documents."
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles = new ArrayList<>();
}
