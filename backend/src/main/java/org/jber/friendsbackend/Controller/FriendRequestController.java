package org.jber.friendsbackend.Controller;

import org.jber.friendsbackend.entities.Account;
import org.jber.friendsbackend.entities.FriendRequest;
import org.jber.friendsbackend.entities.Friends;
import org.jber.friendsbackend.forms.*;
import org.jber.friendsbackend.repositories.FriendRepository;
import org.jber.friendsbackend.repositories.FriendRequestRepository;
import org.jber.friendsbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class FriendRequestController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FriendRequestRepository friendRequestRepo;
    @Autowired
    private FriendRepository friendsRepo;

    @PostMapping("/friendrequest")
    @ResponseBody ResponseEntity friend_request(@RequestBody FriendRequestForm form, @CookieValue(name = "id", defaultValue = "-1") String cookie) {
        Long id = Long.parseLong(cookie);
        if(id == -1)
            return ResponseEntity.badRequest().body("not logged in");

        Optional<Account> requestor = userRepo.findById(id);
        Optional<Account> requested = userRepo.findById(form.getRequested());

        if(!requestor.isEmpty() && !requested.isEmpty()) {
            if(requestor.get() == requested.get())
                return ResponseEntity.badRequest().body("you can't send yourself a friendrequest");

            List<Friends> alreadyFriends = friendsRepo.findByUser1AndUser2(requestor.get(), requested.get());
            alreadyFriends.addAll(friendsRepo.findByUser1AndUser2(requested.get(), requestor.get()));
            if(!alreadyFriends.isEmpty())
                return ResponseEntity.badRequest().body("already friends");

            if(friendRequestRepo.findByRequestorAndRequested(requestor.get(), requested.get()).isEmpty()) {
                FriendRequest friendRequest = new FriendRequest(null, requestor.get(), requested.get());
                friendRequestRepo.save(friendRequest);
                System.out.println(requestor.get().getUsername() + " asked " + requested.get().getUsername() + " to be friends.");
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.ok("exist already");
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/acceptfriendrequest")
    @ResponseBody ResponseEntity accept_friend_request(@RequestBody FriendRequestForm form, @CookieValue(name = "id", defaultValue = "-1") String cookie) {
        Long id = Long.parseLong(cookie);
        if(id == -1)
            return ResponseEntity.badRequest().body("not logged in");

        Optional<Account> requestor = userRepo.findById(form.getRequestor());
        Optional<Account> requested = userRepo.findById(id);
        if(!requested.isEmpty() && !requestor.isEmpty()) {
            List<FriendRequest> requests = friendRequestRepo.findByRequestorAndRequested(requestor.get(), requested.get());
            if(!requests.isEmpty()) {
                Friends newFriends = new Friends(null, requestor.get(), requested.get());
                friendsRepo.save(newFriends);
                friendRequestRepo.delete(requests.get(0));
                System.out.println(requestor.get().getUsername() + " and " + requested.get().getUsername() + " are now friends.");
                return ResponseEntity.ok("success");
            } else
                return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.notFound().build();
    }
    @PostMapping("/denyfriendrequest")
    @ResponseBody ResponseEntity deny_friend_request(@RequestBody FriendRequestForm form, @CookieValue(name = "id", defaultValue = "-1") String cookie) {
        Long id = Long.parseLong(cookie);
        if(id == -1)
            return ResponseEntity.badRequest().body("not logged in");

        Optional<Account> requested = userRepo.findById(id);
        Optional<Account> requestor = userRepo.findById(form.getRequestor());
        if(!requested.isEmpty() && !requestor.isEmpty()) {
            List<FriendRequest> requests = friendRequestRepo.findByRequestorAndRequested(requestor.get(), requested.get());
            if(!requests.isEmpty()) {
                friendRequestRepo.delete(requests.get(0));
                System.out.println(requestor.get().getUsername() + " doesn't want to be friends with  " + requested.get().getUsername() + ".");
                return ResponseEntity.ok("success");
            } else
                return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.notFound().build();
    }
    @PostMapping("/queryfriendrequests")
    @ResponseBody ResponseEntity query_friend_requests(@CookieValue(name = "id", defaultValue = "-1") String cookie) {
        Long id = Long.parseLong(cookie);
        if(id == -1)
            return ResponseEntity.badRequest().body("not logged in");

        Optional<Account> acc = userRepo.findById(id);
        if(!acc.isEmpty()) {
            List<Long> results = friendRequestRepo.findByRequested(acc.get()).stream().map((x) -> x.getRequestor().getId()).toList();
            return ResponseEntity.ok(results);
        }
        else
            return ResponseEntity.notFound().build();
    }
    @PostMapping("/queryfriends")
    @ResponseBody ResponseEntity query_friends(@CookieValue(name = "id", defaultValue = "-1") String cookie) {
        Long id = Long.parseLong(cookie);
        if(id == -1)
            return ResponseEntity.badRequest().body("not logged in");

        Optional<Account> acc = userRepo.findById(id);
        if(!acc.isEmpty()) {
            List<Long> results = friendsRepo.findByUser1OrUser2(acc.get(), acc.get()).stream().map(x -> x.getUser1().equals(acc.get()) ? x.getUser2() : x.getUser1()).map(x -> x.getId()).toList();
            return ResponseEntity.ok(results);
        }
        else
            return ResponseEntity.notFound().build();
    }
}
