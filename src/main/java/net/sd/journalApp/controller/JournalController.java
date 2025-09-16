package net.sd.journalApp.controller;

import net.sd.journalApp.entity.JournalEntity;
import net.sd.journalApp.entity.User;
import net.sd.journalApp.service.JournalEntryService;
import net.sd.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
@CrossOrigin(origins = "*")
public class JournalController {



    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         User userInDB = userService.findByUserName(authentication.getName());
         List<JournalEntity> all = userInDB.getJournalEntries();

         if(all!=null && !all.isEmpty()){
             return new ResponseEntity<>(all,HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping()
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry,userName);

            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // print to console/logs
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntity> GetById(@PathVariable String myid){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntity> journal = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).toList();

        if(!journal.isEmpty()){
            Optional<JournalEntity> JE =  journalEntryService.getByIDEntry(myid);

            if(JE.isPresent()){
                return new ResponseEntity<JournalEntity>(JE.get(), HttpStatus.OK);
            }
        }


        return new ResponseEntity<JournalEntity>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> DeleteById(@PathVariable String myid){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        journalEntryService.deleteEntry(myid,userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("id/{myid}")
    public ResponseEntity<?> updateById(@PathVariable String myid, @RequestBody JournalEntity newEntry){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntity> journal = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).toList();

        if(!journal.isEmpty()){
            Optional<JournalEntity> JE =  journalEntryService.getByIDEntry(myid);

            if(JE.isPresent()){
                JournalEntity oldEntry = JE.get();
                oldEntry.setTitle(newEntry.getTitle());
                oldEntry.setContent(newEntry.getContent());
                // Set other fields if needed
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(HttpStatus.OK);// ✅ pass actual entity
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
