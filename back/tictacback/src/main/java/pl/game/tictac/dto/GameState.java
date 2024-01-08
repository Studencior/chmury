package pl.game.tictac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameState {
    private String[][] board;

    public GameState() {
        this.board = new String[3][3]; // Initialize an empty board
    }

    // Method to update the board
    public void updateBoard(int row, int column, char playerSymbol) {
        if (row >= 0 && row < 3 && column >= 0 && column < 3) {
            board[row][column] = String.valueOf(playerSymbol);
        }
    }

    // Standard getters and setters
}
