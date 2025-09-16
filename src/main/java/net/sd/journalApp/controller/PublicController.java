package net.sd.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.sd.journalApp.entity.User;
import net.sd.journalApp.service.UserDetialsImp;
import net.sd.journalApp.service.UserService;
import net.sd.journalApp.service.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/public")
@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetialsImp userDetialsImp;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }


    @PostMapping("/signup")
    public void signup(@RequestBody User user){
        userService.saveEntry(user);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

            UserDetails userDetails = userDetialsImp.loadUserByUsername(user.getUserName());

            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Exception occurred while createAuthenticationToken " + e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }



    }
}
