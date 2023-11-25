package org.jber.friendsbackend.repositories;

import org.jber.friendsbackend.entities.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<Account, Long> {
    Optional<Account> findById(Long Id);
    List<Account> findByUsernameIgnoreCase(String username);
    List<Account> findByUsernameIgnoreCaseContaining(String username);
    List<Account> findByEmail(String email);
    List<Account> findByEmailIgnoreCaseContaining(String email);
}
