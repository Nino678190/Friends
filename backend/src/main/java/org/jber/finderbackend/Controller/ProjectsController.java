package org.jber.finderbackend.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.jber.finderbackend.entities.Account;
import org.jber.finderbackend.entities.FriendRequest;
import org.jber.finderbackend.entities.Friends;
import org.jber.finderbackend.entities.Project;
import org.jber.finderbackend.forms.*;
import org.jber.finderbackend.repositories.FriendRepository;
import org.jber.finderbackend.repositories.FriendRequestRepository;
import org.jber.finderbackend.repositories.ProjectRepository;
import org.jber.finderbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ProjectsController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProjectRepository projectRepo;
    @PostMapping("/createproject")
    @ResponseBody ResponseEntity create_project(@RequestBody ProjectForm form, @CookieValue(name = "id", defaultValue = "-1") String cookie) {
        Long id = Long.parseLong(cookie);
        if(id == -1)
            return ResponseEntity.badRequest().body("not logged in");

        Optional<Account> acc = userRepo.findById(id);
        if(acc.isEmpty())
            return ResponseEntity.badRequest().body("youre dumb");

        Project newProject = new Project(null, form.getTitle(), form.getDescription(), 0L, List.of(acc.get()));
        projectRepo.save(newProject);
        return ResponseEntity.ok("success");
    }
    @PostMapping("/searchprojects")
    @ResponseBody ResponseEntity search_projects(@RequestBody SearchForm form) {
        List<Project> projects = new ArrayList<>();
        projects.addAll(projectRepo.findByTitleIgnoreCaseContaining(form.getSearch()));
        projects.addAll(projectRepo.findByDescriptionIgnoreCaseContaining(form.getSearch()));
        return ResponseEntity.ok(projects.stream().map(x -> x.getId()));
    }
    @PostMapping("/joinproject")
    @ResponseBody ResponseEntity join_project(@RequestBody IDForm form, @CookieValue(name = "id", defaultValue = "-1") String cookie) {
        Long id = Long.parseLong(cookie);
        if(id == -1)
            return ResponseEntity.badRequest().body("not logged in");

        Optional<Account> acc = userRepo.findById(id);
        Optional<Project> project = projectRepo.findById(form.getId());

        if(project.isEmpty() || acc.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<Project> alreadyJoined = projectRepo.findByIdAndMembers(project.get().getId(), acc.get());
        if(!alreadyJoined.isEmpty())
            return ResponseEntity.badRequest().body("already joined");
        project.get().getMembers().add(acc.get());
        projectRepo.save(project.get());

        return ResponseEntity.ok("success");
    }
    @PostMapping("/leaveproject")
    @ResponseBody ResponseEntity leave_project(@RequestBody IDForm form, @CookieValue(name = "id", defaultValue = "-1") String cookie) {
        Long id = Long.parseLong(cookie);
        if(id == -1)
            return ResponseEntity.badRequest().body("not logged in");

        Optional<Account> acc = userRepo.findById(id);
        Optional<Project> project = projectRepo.findById(form.getId());

        if(project.isEmpty() || acc.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<Project> joined = projectRepo.findByIdAndMembers(project.get().getId(), acc.get());
        if(joined.isEmpty())
            return ResponseEntity.badRequest().body("not in project");

        project.get().getMembers().remove(acc.get());
        projectRepo.save(project.get());

        return ResponseEntity.ok("success");
    }
    @PostMapping("/queryproject")
    @ResponseBody ResponseEntity query_project(@RequestBody IDForm form) {
        Optional<Project> p = projectRepo.findById(form.getId());
        if(p.isEmpty())
            return ResponseEntity.notFound().build();
        Project project = p.get();

        return ResponseEntity.ok(new ProjectForm(project.getTitle(), project.getDescription(), project.getMembers().stream().map(x -> x.getId()).toList()));
    }
}
