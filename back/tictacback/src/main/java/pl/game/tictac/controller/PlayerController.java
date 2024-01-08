package pl.game.tictac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.game.tictac.dto.JwtResponse;
import pl.game.tictac.dto.LoginRequest;
import pl.game.tictac.dto.SignupRequest;
import pl.game.tictac.model.Player;
import pl.game.tictac.service.PlayerService;

import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/api/user")
// @CrossOrigin(origins = "http://localhost:4200") // Angular app address
public class PlayerController {

    @Autowired
    private PlayerService playerService; // You will need to implement this service
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/leaderboard")
    public ResponseEntity<List<Player>> getLeaderboard() {
        // Implementation to get top 10 players with the most wins
        return ResponseEntity.ok().body(playerService.getLeaderboard());
    }
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) {
        try {
            String jwt = playerService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(Collections.singletonMap("token", jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        String token = playerService.signup(signupRequest);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    // ... other endpoints related to user ...
}
