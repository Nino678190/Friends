package org.jber.friendsbackend.repositories;

import org.jber.friendsbackend.entities.Account;
import org.jber.friendsbackend.entities.Friends;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendRepository extends CrudRepository<Friends, Long> {
    List<Friends> findByUser1OrUser2(Account user1, Account user2);
    List<Friends> findByUser1AndUser2(Account user1, Account user2);
}
