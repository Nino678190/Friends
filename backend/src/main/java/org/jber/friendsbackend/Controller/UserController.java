package org.jber.friendsbackend.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.jber.friendsbackend.entities.*;
import org.jber.friendsbackend.forms.*;
import org.jber.friendsbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private InterestsRepository interestsRepo;
    @PostMapping("/login")
    @ResponseBody ResponseEntity login(@RequestBody LoginForm form, HttpServletResponse http) {
        List<Account> acc = userRepo.findByUsernameIgnoreCase(form.getUsername());
        acc.addAll(userRepo.findByEmail(form.getUsername()));
        if(acc.isEmpty())
            return ResponseEntity.notFound().build();
        else if(acc.get(0).getPassword().equals(form.getPassword())) {
            System.out.println(form.getUsername() + " logged in.");

            http.addCookie(new Cookie("id", acc.get(0).getId().toString()));
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().body("wrong password");
    }
    @PostMapping("/signup")
    @ResponseBody ResponseEntity signup(@RequestBody SignupForm form) {
        if(userRepo.findByUsernameIgnoreCase(form.getUsername()).isEmpty()) {
            Account account = new Account(null, form.getEmail(), form.getUsername(), form.getPassword(), form.getBirthdate(), form.getPronouns(), List.of());
            userRepo.save(account);

            System.out.println(form.getUsername() + " signed up.");
            return ResponseEntity.ok("success");
        } else
            return ResponseEntity.badRequest().body("already exists");
    }
    @PostMapping("/searchuser")
    @ResponseBody ResponseEntity search(@RequestBody SearchForm form) {
        List<Account> accounts = new ArrayList<>();

        accounts.addAll(userRepo.findByUsernameIgnoreCaseContaining(form.getSearch()));
        accounts.addAll(userRepo.findByEmailIgnoreCaseContaining(form.getSearch()));

        return ResponseEntity.ok(accounts.stream().map(x -> x.getId()));
    }
    @PostMapping("/queryuser")
    @ResponseBody ResponseEntity query_user(@RequestBody IDForm form) {
        Optional<Account> acc = userRepo.findById(form.getId());
        if(acc.isPresent()) {
            acc.get().setPassword(null);
            return ResponseEntity.ok(acc);
        }
        else
            return ResponseEntity.notFound().build();
    }
    @GetMapping("/list")
    @ResponseBody String list() {
        return userRepo.findAll().toString();
    }
    @GetMapping("/debug")
    @ResponseBody ResponseEntity debug() {

        return ResponseEntity.ok(List.of("programming"));
    }
}
