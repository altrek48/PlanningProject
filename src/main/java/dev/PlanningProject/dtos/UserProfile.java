package dev.PlanningProject.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    private Long id;
    private String username;
    private String email;
    private String avatarUrl;

}
