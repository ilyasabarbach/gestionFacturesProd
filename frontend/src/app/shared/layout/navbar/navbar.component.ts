import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Pour *ngIf et AsyncPipe
import { RouterModule } from '@angular/router'; // Pour routerLink
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  // On récupère l'état de connexion directement depuis le service
  isLoggedIn$ = this.authService.isLoggedIn$;

  constructor(private authService: AuthService) {}

  onLogout(): void {
    this.authService.logout();
  }
}
