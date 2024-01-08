import { Component } from '@angular/core';
import { GameApiService } from '../game-api.service'; // Adjust the path as needed
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent {
  username: string = '';
  password: string = '';

  constructor(private gameApiService: GameApiService, private router: Router) {}

  onSignup(): void {
    this.gameApiService.signup(this.username, this.password).subscribe(
      (response) => {
        console.log('Signup successful', response);

        // Assuming the JWT is in the response body and named 'token'
        const token = response.token;

        // Store the JWT in local storage or session storage
        localStorage.setItem('jwtToken', token);

        // Navigate to a protected route like '/dashboard' or '/home'
        // Make sure to implement route protection in your routing module
        this.router.navigate(['/dashboard']);
      },
      (error) => {
        console.error('Signup failed', error);
      }
    );
  }
}
