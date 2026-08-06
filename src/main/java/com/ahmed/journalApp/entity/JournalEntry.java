package com.ahmed.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection ="journal_entries") // @Document tells Springs that ki ye jo document hai MongoDb k collection se mapped entity hai
@Data
@NoArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title ;
    private String content;
    private LocalDateTime date;
}
