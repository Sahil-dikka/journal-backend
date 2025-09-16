package net.sd.journalApp.repository;

import net.sd.journalApp.entity.JournalEntity;
import net.sd.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

    User findByUserName(String userName);

    void deleteByUserName(String userName);
}
