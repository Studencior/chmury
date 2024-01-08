package pl.game.tictac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CloseGameRequest {
    private Long winnerId; // Can be null if it's a draw

    // Standard getters and setters
}
