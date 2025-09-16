package net.sd.journalApp.controller;

import net.sd.journalApp.entity.JournalEntity;
import net.sd.journalApp.entity.User;
import net.sd.journalApp.repository.UserRepository;
import net.sd.journalApp.service.JournalEntryService;
import net.sd.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")

public class UserController {

   @Autowired
   private UserService userService;

    @Autowired
    private UserRepository userRepository;

   @GetMapping
   public List<User> getAll(){
       return userService.getEntry();
   }




   @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName = authentication.getName();
       User userPresent = userService.findByUserName(userName);
       userPresent.setUserName(user.getUserName());
       userPresent.setPassword(user.getPassword());
       userService.saveEntry(userPresent);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
