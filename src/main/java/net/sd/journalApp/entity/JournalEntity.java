package net.sd.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class JournalEntity {

    @Id
    private String id;

    @NonNull
    private String title;

    private String content;

}
