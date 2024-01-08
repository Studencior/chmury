package pl.game.tictac.dto;

import lombok.Getter;
import lombok.Setter;
import pl.game.tictac.model.Game;
import pl.game.tictac.model.Player;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class GameUpdate {
    private Long gameId;
    private PlayerInfo player1;
    private PlayerInfo player2;
    private String status;
    private String winner; // Can be a username or 'draw'

    public GameUpdate(Game game) {
        this.gameId = game.getId();
        this.status = game.getStatus();
        this.winner = game.getWinner();
        this.player1 = game.getPlayer1() != null ? new PlayerInfo(game.getPlayer1()) : null;
        this.player2 = game.getPlayer2() != null ? new PlayerInfo(game.getPlayer2()) : null;
    }

    // Getters and Setters

    // PlayerInfo inner class or separate DTO
    @Getter
    @Setter
    public static class PlayerInfo {
        private Long playerId;
        private String username;

        public PlayerInfo(Player player) {
            this.playerId = player.getId();
            this.username = player.getUsername();
        }

        // Getters and Setters
    }
}
