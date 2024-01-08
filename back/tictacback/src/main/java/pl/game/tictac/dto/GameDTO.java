package pl.game.tictac.dto;

public class GameDTO {
    private Long gameId;
    private String gameState;
    private Character winner; // Używam klasy Character zamiast typu prymitywnego char, aby umożliwić null

    // Konstruktor
    public GameDTO(Long gameId, String gameState, Character winner) {
        this.gameId = gameId;
        this.gameState = gameState;
        this.winner = winner;
    }

    // Gettery i Settery
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public Character getWinner() {
        return winner;
    }

    public void setWinner(Character winner) {
        this.winner = winner;
    }
}

