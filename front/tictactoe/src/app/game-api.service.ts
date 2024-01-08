import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Game, Move, Player } from './game.model'; // Import the Game model according to your structure
import { getPlayerIdFromToken } from './jwt.util';
import { getPlayerUsernameFromToken } from './jwt.util';

@Injectable({
  providedIn: 'root',
})
export class GameApiService {
  private apiUrl = 'http://54.174.68.163:8080/api'; // URL to your Spring Boot API
  // private wsUrl = 'http://localhost:8080/tic-tac-toe-websocket'; // WebSocket endpoint
  // private socket$: WebSocketSubject<any> | undefined;

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    const url = `${this.apiUrl}/user/login`;
    return this.http.post(
      url,
      { username, password },
      { responseType: 'json' }
    );
  }

  signup(username: string, password: string): Observable<any> {
    const url = `${this.apiUrl}/user/signup`;
    return this.http.post(
      url,
      { username, password },
      { responseType: 'text' }
    );
  }
  joinGame(gameId: number): Observable<any> {
    const userId = getPlayerIdFromToken();
    return this.http.post<any>(`${this.apiUrl}/game/join/${gameId}`, {
      userId: userId,
    });
  }
  logout(): void {
    localStorage.removeItem('jwtToken');

    // Handle other logout actions like redirecting the user
  }
  getAvailableGames(): Observable<Game[]> {
    return this.http.get<Game[]>(`${this.apiUrl}/game/available-games`);
  }



  createGame(playerId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/game/create`, { playerId: playerId });
  }

  closeGame(gameId: string): Observable<any> {
    const url = `${this.apiUrl}/game/${gameId}/close`;
    return this.http.post(url, {});
  }

  getGameState(gameId: number): Observable<Game> {
    return this.http.get<Game>(`${this.apiUrl}/game/${gameId}`);
  }

  makeMove(gameId: number, moveRequest: any): Observable<Game> {
    return this.http.post<Game>(`${this.apiUrl}/game/${gameId}/move`, moveRequest);
  }
  getLeaderboard(): Observable<Player[]> {
    return this.http.get<Player[]>(`${this.apiUrl}/user/leaderboard`);
  }
}
