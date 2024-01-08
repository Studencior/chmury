package pl.game.tictac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.game.tictac.dto.*;
import pl.game.tictac.model.Game;
import pl.game.tictac.model.Player;
import pl.game.tictac.repository.PlayerRepository;
import pl.game.tictac.service.GameService;

import java.util.List;
@RestController
@RequestMapping("/api/game")
// @CrossOrigin(origins = "http://localhost:4200") // Angular app address
public class GameController {

    @Autowired
    private GameService gameService; // You will need to implement this service

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/available-games")
    public ResponseEntity<List<Game>> getAvailableGames() {
        // Implementation to get all games waiting for a second player
        return ResponseEntity.ok().body(gameService.getAvailableGames());
    }

    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestBody GameCreationRequest request) {
        try {
            Game game = gameService.createGame(request.getPlayerId());
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{gameId}/close")
    public ResponseEntity<?> closeGame(@PathVariable Long gameId, @RequestBody CloseGameRequest closeRequest) {
        try {
            gameService.closeGame(gameId, closeRequest.getWinnerId());
            return ResponseEntity.ok().body("Game closed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/join/{gameId}")
    public ResponseEntity<?> joinGame(@PathVariable Long gameId, @RequestBody GameJoinRequest gameJoinRequest) {
        try {
            Player player = playerRepository.findById(gameJoinRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("Player not found"));

            Game game = gameService.joinGame(gameId, player.getId());
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            // Custom exception handling as necessary
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/{gameId}/move")
    public ResponseEntity<Game> makeMove(@PathVariable Long gameId, @RequestBody MoveRequest moveRequest) {
        Game game = gameService.makeMove(gameId, moveRequest);
        return ResponseEntity.ok(game);
    }
    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGameState(@PathVariable Long gameId) {
        Game game = gameService.getGameState(gameId);
        return ResponseEntity.ok(game);
    }
    // ... other endpoints related to game ...
}
