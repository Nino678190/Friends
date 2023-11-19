package org.jber.finderbackend.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectForm {
    private String title;
    private String description;
    private List<Long> members;
}
