import { Component } from '@angular/core';
import { GameApiService } from './game-api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'tictactoe';
  constructor(private gameApiService: GameApiService, private router: Router) {}

  onLogout(): void {
    this.gameApiService.logout();
    this.router.navigate(['/login']);
  }
}
