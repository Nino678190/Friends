package org.jber.friendsbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long Id;
    private String title;
    private String description;
    private Long imageId;
    @ManyToMany
    private List<Account> members;
}
