package net.sd.journalApp.service;

import net.sd.journalApp.entity.JournalEntity;
import net.sd.journalApp.entity.User;
import net.sd.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

//    @Transactional
    public void saveEntry(JournalEntity entry, String userName) {
        User user = userService.findByUserName(userName);

        JournalEntity saved = journalEntryRepository.save(entry);
        user.getJournalEntries().add(saved);

        userService.saveUser(user);
    }

    public void saveEntry(JournalEntity entry) {

        journalEntryRepository.save(entry);
    }
    public List<JournalEntity> getEntry(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> getByIDEntry(String id){
        return  journalEntryRepository.findById(id);
    }

    public void deleteEntry(String id, String userName){
        User user = userService.findByUserName(userName);
        //System.out.println("id" + user.getId());
         user.getJournalEntries().removeIf(x -> x.getId().equals(id));
         userService.saveUser(user);
        journalEntryRepository.deleteById(id);

    }

    public void updateEntry(String id){

    }
}
