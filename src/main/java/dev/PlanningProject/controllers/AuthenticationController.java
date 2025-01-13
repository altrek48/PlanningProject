package dev.PlanningProject.controllers;

import dev.PlanningProject.dtos.auth.JwtRequest;
import dev.PlanningProject.dtos.auth.JwtResponse;
import dev.PlanningProject.dtos.auth.SignInRequest;
import dev.PlanningProject.services.GroupService;
import dev.PlanningProject.services.auth.JwtUserDetailsService;
import dev.PlanningProject.services.auth.TokenManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class AuthenticationController {

    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final GroupService groupService;

    @PostMapping("login")
    public JwtResponse createToken(@RequestBody JwtRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }
        catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        return new JwtResponse(tokenManager.generateJwtToken(userDetails));
    }

    @PostMapping(value = "register", consumes = "application/json")
    public void addUser(@RequestBody SignInRequest request) {
        userDetailsService.createUser(request);
    }

    @GetMapping(value = "base/isGroupCreator/{groupId}")
    public boolean isGroupCreator(@PathVariable("groupId") Long groupId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return groupService.isUserCreator(username, groupId);
    }


}
