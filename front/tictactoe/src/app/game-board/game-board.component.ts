import { Component, OnInit } from '@angular/core';
import { GameApiService } from '../game-api.service';
import { interval, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { Game } from '../game.model';
import { getPlayerIdFromToken } from '../jwt.util';
import { getPlayerUsernameFromToken } from '../jwt.util';
import { Router } from '@angular/router';

@Component({
  selector: 'app-game-board',
  templateUrl: './game-board.component.html',
  styleUrls: ['./game-board.component.scss'],
})
export class GameBoardComponent implements OnInit {
  board: string[][] = [];
  playerUsername = getPlayerUsernameFromToken()
  winner?: string;

  isDraw: boolean = false;
  private pollingSubscription!: Subscription;
  game!: Game;
  canPlayer1Move: boolean = false;
  isPlayerTurn: boolean = false;

  constructor(private gameApiService: GameApiService, private router: Router) {}

  ngOnInit(): void {
    this.loadGameFromLocalStorage();
    this.startPolling();
  }
  private loadGameFromLocalStorage(): void {
    const gameData = localStorage.getItem('currentGame');
    if (gameData) {
      this.game = JSON.parse(gameData);
      this.updateGameLogic();

      // Now you can use 'this.game' in your component
    }
  }
  startPolling(): void {
    this.pollingSubscription = interval(3000)
      .pipe(switchMap(() => this.gameApiService.getGameState(this.game.id)))
      .subscribe((game) => {
        this.game = game;
        console.log(game);
        this.updateGameLogic();
        this.updateBoard();
        localStorage.setItem('currentGame', JSON.stringify(game));

      });
  }

  updateGameLogic(): void {
    if (this.game && this.game.status === 'IN_PROGRESS') {
      // Determine if it's the current player's turn
      this.isPlayerTurn = this.game.currentPlayer === this.playerUsername;
    } else {
      this.isPlayerTurn = false;
    }
  }
  
  makeMove(row: number, col: number): void {
    console.log(this.isPlayerTurn)
    if (this.isPlayerTurn && this.board[row][col] === ' ') {
      // Update the board optimistically
      const playerUsername = getPlayerUsernameFromToken()

      const moveRequest = {
        player: playerUsername,
        row: row,
        column: col,
        symbol: this.currentPlayerSymbol(),
      };
      this.board[row][col] = this.currentPlayerSymbol();
  
      // Send move to backend
      this.gameApiService.makeMove(this.game.id, moveRequest).subscribe(
        updatedGame => {
          this.game = updatedGame;
          this.updateBoard();
          // this.checkIfPlayersTurn();
        },
        error => {
          console.error('Error making move:', error);
          // Revert the optimistic update
          this.board[row][col] = '';
        }
      );
    }
  }
  updateBoard(): void {
    // Convert the board string to a 2D array
    this.board = [];
    for (let i = 0; i < 3; i++) {
      this.board[i] = this.game.board.slice(i * 3, (i + 1) * 3).split('');
    }
    if(this.game.winner !== null ){
      if (this.pollingSubscription) {
        this.pollingSubscription.unsubscribe();
      }
      this.winner = this.game.winner;
    }
  }

  currentPlayerSymbol(): any {
    // Determine the symbol based on the player's ID
    return this.game.player1.username === this.playerUsername ? 'X' : 'O';
  }

  redirectToDashboard() {
    this.router.navigate(['/dashboard']);
  }
  ngOnDestroy(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
    localStorage.removeItem('currentGame');

  }
}
