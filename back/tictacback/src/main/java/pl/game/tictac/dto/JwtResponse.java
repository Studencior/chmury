package pl.game.tictac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private final String jwt;

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    // Getters...
}
