package org.jber.finderbackend.repositories;

import org.jber.finderbackend.entities.Account;
import org.jber.finderbackend.entities.FriendRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
    Optional<FriendRequest> findById(Long id);
    List<FriendRequest> findByRequestorAndRequested(Account requestor, Account requested);
    List<FriendRequest> findByRequested(Account requested);

}
