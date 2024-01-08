package pl.game.tictac.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameMove {
    private Long playerId;
    private int row;
    private int column;
    private char playerSymbol; // 'X' or 'O'

    // Standard getters and setters
}
