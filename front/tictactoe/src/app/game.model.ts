export interface Game {
  id: number;
  status: string; // e.g., 'WAITING_FOR_PLAYER', 'IN_PROGRESS', 'FINISHED'
  player1: Player; // Assuming a 'Player' interface also exists
  player2?: Player;
  board: string; // Or any structure you use to represent the game board
  currentPlayer?: string; 
  winner?: string;// Optional since there may not be a second player yet
  // Add other properties that a game might have, like scores, moves, etc.
}

export interface Player {
  id: number;
  username: string;
  wins: number;
  // Include other player-related properties as necessary
}
export interface Move {
  position: number; // The position on the board where the player wants to place their symbol
  symbol: 'X' | 'O'; // Assuming 'X' for player one and 'O' for player two
}
