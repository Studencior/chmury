package pl.game.tictac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameJoinMessage {
    private String username; // Assuming the username is used to identify the player

    // Default constructor for JSON deserialization
    public GameJoinMessage() {
    }

    // Constructor, Getters, and Setters
}

