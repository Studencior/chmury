package pl.game.tictac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveRequest {
    private char symbol; // 'X' or 'O'
    private String player;
    private int row;
    private int column;

}
