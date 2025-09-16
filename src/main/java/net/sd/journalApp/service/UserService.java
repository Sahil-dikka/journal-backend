package net.sd.journalApp.service;

import net.sd.journalApp.entity.JournalEntity;
import net.sd.journalApp.entity.User;
import net.sd.journalApp.repository.JournalEntryRepository;
import net.sd.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User entry) {
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        entry.setRoles(Arrays.asList("USER"));
         userRepository.save(entry);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }


    public List<User> getEntry(){
        return userRepository.findAll();
    }

    public Optional<User> getByIDEntry(String id){
        return  userRepository.findById(id);
    }

    public User deleteEntry(String id){
        userRepository.deleteById(id);
        return null;
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void updateEntry(String id){

    }
}
