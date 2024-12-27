package dev.PlanningProject.dtos.auth;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;
    private String password;

}
