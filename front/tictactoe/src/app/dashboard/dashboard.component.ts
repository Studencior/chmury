import { Component, OnInit } from '@angular/core';
import { GameApiService } from '../game-api.service';
import { Router } from '@angular/router';
import { getPlayerIdFromToken } from '../jwt.util';
import { Game, Player } from '../game.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  users: any[] = []; // Placeholder for users leaderboard
  availableGames: Game[] = [];

  constructor(private gameApiService: GameApiService, private router: Router) {}

  ngOnInit(): void {
    this.fetchAvailableGames();
    this.fetchLeaderboard();
  }
  fetchLeaderboard() {
    this.gameApiService.getLeaderboard().subscribe((data: Player[]) => {
      this.users = data;
    });
  }
  fetchAvailableGames(): void {
    this.gameApiService.getAvailableGames().subscribe(
      (games) => {
        this.availableGames = games;
      },
      (error) => {
        console.error('Failed to fetch available games', error);
      }
    );
  }
  joinGame(gameId: number): void {
    this.gameApiService.joinGame(gameId).subscribe(
      (game) => {
        console.log('Joined game successfully', game);
        localStorage.setItem('currentGame', JSON.stringify(game));
        this.router.navigate(['/game-board', game.id]); // Adjust route as needed
      },
      (error) => console.error('Failed to join game', error)
    );
  }

  startNewGame(): void {
    const playerId = getPlayerIdFromToken();
    if (playerId) {
      this.gameApiService.createGame(playerId).subscribe(
        (game) => {
          console.log('New game created', game);
          localStorage.setItem('currentGame', JSON.stringify(game));

          this.router.navigate(['/game-board', game.id]); // Adjust route as needed
        },
        (error) => {
          console.error('Failed to create new game', error);
        }
      );
    } else {
      console.error('Player ID could not be retrieved from token');
      // Handle the error appropriately
    }
  }
}
