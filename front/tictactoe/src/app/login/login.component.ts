import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { GameApiService } from '../game-api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  displaySignUpForm: boolean = false;
  username: string = '';
  password: string = '';

  constructor(private gameApiService: GameApiService, private router: Router) {}

  onLogin(): void {
    this.gameApiService.login(this.username, this.password).subscribe(
      (response) => {
        console.log('Login successful', response);
        localStorage.setItem('jwtToken', response.token); // Assuming the token is in the `token` property
        this.router.navigate(['/dashboard']);
      },
      (error) => {
        console.error('Login failed', error);
      }
    );
  }

  showSignUp(): void {
    this.displaySignUpForm = true;
  }
}
