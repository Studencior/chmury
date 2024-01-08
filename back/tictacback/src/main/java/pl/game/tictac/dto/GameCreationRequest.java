package pl.game.tictac.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameCreationRequest {
    private Long playerId; // The ID of the player who initiates the game

    // Standard getters and setters
}

