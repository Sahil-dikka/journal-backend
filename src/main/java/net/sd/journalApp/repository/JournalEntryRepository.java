package net.sd.journalApp.repository;

import net.sd.journalApp.entity.JournalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntity,String> {


}
