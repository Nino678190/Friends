package org.jber.friendsbackend.repositories;

import org.jber.friendsbackend.entities.Account;
import org.jber.friendsbackend.entities.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Long> {
        List<Project> findByTitleIgnoreCaseContaining(String title);
        List<Project> findByDescriptionIgnoreCaseContaining(String description);
        Optional<Project> findByIdAndMembers(Long id, Account member);
}
