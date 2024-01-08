package pl.game.tictac.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
//@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private Player player2;

    private String winner; // Player username or 'draw'

    @Column
    private String status; // e.g., "WAITING_FOR_PLAYER", "IN_PROGRESS", "COMPLETED"

    private String currentPlayer; // Stores the username or ID of the current player

    private String board; // Representing the board as a string

    public Game() {
        // Represent empty cells with spaces in a 3x3 grid
        this.board = "         ";
    }
//    private String board; // You might store the board as a JSON string or in a more complex structure

    // Standard getters and setters
}
