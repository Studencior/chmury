import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GameBoardComponent } from './game-board/game-board.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
  { path: 'game', component: GameBoardComponent },
  { path: 'signup', component: SignupComponent }, // Set LoginComponent as the default route
  { path: 'dashboard', component: DashboardComponent }, // Add the dashboard route
  { path: 'login', component: LoginComponent }, // Set LoginComponent as the default route
  { path: 'game-board/:id', component: GameBoardComponent }, // Adjust the component name as needed

  // { path: '', redirectTo: '/game', pathMatch: 'full' }, // Redirect to the game board by default
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
