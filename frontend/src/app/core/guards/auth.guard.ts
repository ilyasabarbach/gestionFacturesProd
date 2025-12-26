import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean | UrlTree {
    const token = this.authService.getToken();

    if (token) {
      return true; // L'utilisateur est connecté, on le laisse passer
    } else {
      // Pas connecté ? On redirige vers le login
      return this.router.createUrlTree(['/login']);
    }
  }
}
