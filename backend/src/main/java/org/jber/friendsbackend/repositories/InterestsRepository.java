package org.jber.friendsbackend.repositories;

import org.jber.friendsbackend.entities.Interest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InterestsRepository extends CrudRepository<Interest, Long> {
    boolean existsByName(String name);
    List<Interest> findByNameIgnoreCase(String name);
}
