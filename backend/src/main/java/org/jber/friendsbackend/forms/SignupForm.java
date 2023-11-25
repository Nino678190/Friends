package org.jber.friendsbackend.forms;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Getter
public class SignupForm {
    private String username;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String pronouns;
    private List<String> interests;
}
