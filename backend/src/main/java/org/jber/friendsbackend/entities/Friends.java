package org.jber.friendsbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Friends {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Account user1;
    @ManyToOne
    private Account user2;
}
