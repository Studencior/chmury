package pl.game.tictac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.game.tictac.dto.SignupRequest;
import pl.game.tictac.model.Player;
import pl.game.tictac.repository.PlayerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMillis;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public List<Player> getAllUsers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getUserById(Long id) {
        return playerRepository.findById(id);
    }

    public Player findByUsername(String username) {
        return playerRepository.findByUsername(username).orElse(null);
    }
    public String login(String username, String password) {
        Player foundPlayer = findByUsername(username);
        if (foundPlayer != null && passwordEncoder.matches(password, foundPlayer.getPassword())) {
            return Jwts.builder()
                    .setSubject(foundPlayer.getUsername())
                    .claim("userId", foundPlayer.getId()) // Include the user ID in the token
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }


    public String signup(SignupRequest signupRequest) {
        // Encrypt the password
        Player newPlayer = new Player();
        newPlayer.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        newPlayer.setUsername(signupRequest.getUsername());
        // Save the user to the database
        playerRepository.save(newPlayer);

        // Generate JWT token
        return Jwts.builder()
                .setSubject(newPlayer.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public List<Player> getLeaderboard() {
        return playerRepository.findTop10ByOrderByWinsDesc();
    }
}