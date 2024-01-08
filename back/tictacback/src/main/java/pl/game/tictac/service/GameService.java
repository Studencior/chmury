package pl.game.tictac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.game.tictac.dto.GameDTO;
import pl.game.tictac.dto.GameMove;
import pl.game.tictac.dto.GameState;
import pl.game.tictac.dto.MoveRequest;
import pl.game.tictac.model.Game;
import pl.game.tictac.model.Player;
import pl.game.tictac.repository.GameRepository;
import pl.game.tictac.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;
    public List<Game> getAvailableGames() {
        // Fetch games that are waiting for a second player
        return gameRepository.findByStatus("WAITING_FOR_PLAYER");
    }

    public GameState processMove(Long gameId, GameMove move) {
        // Retrieve game by gameId and validate the move
        // For example, check if it's the player's turn and if the move is legal

        GameState gameState = new GameState(); // Retrieve or create a new GameState
        gameState.updateBoard(move.getRow(), move.getColumn(), move.getPlayerSymbol());

        // You can also add logic to check for game over conditions here

        return gameState;
    }

    public Game createGame(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        Game newGame = new Game();
        newGame.setPlayer1(player);
        newGame.setStatus("WAITING_FOR_PLAYER"); // Set initial game status
        return gameRepository.save(newGame);
    }
    public void closeGame(Long gameId, Long winnerId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (winnerId != null) {
            Player winner = playerRepository.findById(winnerId)
                    .orElseThrow(() -> new RuntimeException("Player not found"));
            game.setWinner(winner.getUsername());
        }

        game.setStatus("FINISHED");
        gameRepository.save(game);
    }
    public Game joinGame(Long gameId, Long userId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Player player = playerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        if ("WAITING_FOR_PLAYER".equals(game.getStatus())) {
            game.setPlayer2(player);
            game.setCurrentPlayer(game.getPlayer1().getUsername());
            game.setStatus("IN_PROGRESS"); // Update game status
            return gameRepository.save(game);
        } else {
            throw new IllegalStateException("Game is not waiting for a player");
        }
    }
    public Game makeMove(Long gameId, MoveRequest moveRequest) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        int index = moveRequest.getRow() * 3 + moveRequest.getColumn();
        char[] boardArray = game.getBoard().toCharArray();

        // Check if the cell is already occupied
        if (boardArray[index] != ' ') {
            throw new IllegalArgumentException("Cell is already occupied");
        }

        // Update the board
        boardArray[index] = moveRequest.getSymbol();
        game.setBoard(new String(boardArray));

        if (isWinner(boardArray, moveRequest.getSymbol())) {
            game.setWinner(game.getCurrentPlayer());
            game.setStatus("COMPLETED");
            incrementPlayerWins(game.getCurrentPlayer());

        } else {
            // Switch current player
            if (game.getPlayer1().getUsername().equals(game.getCurrentPlayer())) {
                game.setCurrentPlayer(game.getPlayer2().getUsername());
            } else {
                game.setCurrentPlayer(game.getPlayer1().getUsername());
            }
        }

        // Update the game state (current player, check for win/draw, etc.)

        return gameRepository.save(game);
    }
    private boolean isWinner(char[] boardArray, char symbol) {
        // Check rows
        for (int i = 0; i < 9; i += 3) {
            if (boardArray[i] == symbol && boardArray[i + 1] == symbol && boardArray[i + 2] == symbol) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (boardArray[i] == symbol && boardArray[i + 3] == symbol && boardArray[i + 6] == symbol) {
                return true;
            }
        }

        // Check diagonals
        if (boardArray[0] == symbol && boardArray[4] == symbol && boardArray[8] == symbol) {
            return true;
        }
        if (boardArray[2] == symbol && boardArray[4] == symbol && boardArray[6] == symbol) {
            return true;
        }

        // If none of the conditions are met, the player has not won yet
        return false;
    }
    private void incrementPlayerWins(String username) {
        Player player = playerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        player.setWins(player.getWins() + 1);
        playerRepository.save(player);
    }
    private String getNextPlayer(Game game, String currentPlayer) {
        // Assuming you have methods to get player1 and player2 from the Game entity
        String player1 = game.getPlayer1().getUsername();
        String player2 = game.getPlayer2().getUsername();

        // Check who the current player is and return the other player
        if (currentPlayer.equals(player1)) {
            return player2;
        } else {
            return player1;
        }
    }
    public Game getGameState(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }
    // Add other methods like joinGame, makeMove, closeGame, etc.
}
