import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  // Route par défaut : redirige vers login
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  // Page de connexion (Accessible à tous)
  { path: 'login', component: LoginComponent },

  // Exemple de page protégée (Le Dashboard ou la liste des clients)
  // Nous la créerons juste après, pour l'instant ça redirige vers login si on n'est pas connecté
  {
    path: 'clients',
    loadComponent: () =>
      import('./features/clients/client-list/client-list.component').then(
        (m) => m.ClientListComponent
      ),
    canActivate: [AuthGuard],
  },

  // Redirection si URL inconnue
  { path: '**', redirectTo: '/login' },
];
