package dev.PlanningProject.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

    @NotBlank
    @Size(min = 5, max = 36, message = "Username must be between 5 and 36 characters long")
    private String username;

    @NotBlank
    @Size(min = 2, max = 36, message = "Password must be between 5 and 36 characters long")
    private String password;

}
