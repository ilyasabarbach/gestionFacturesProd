import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';
import { ClientListComponent } from './features/clients/client-list/client-list.component';
import { ClientFormComponent } from './features/clients/client-form/client-form.component';
import { ProduitListComponent } from './features/produits/produit-list/produit-list.component';
import { ProduitFormComponent } from './features/produits/produit-form/produit-form.component';
// Import Devis
import { DevisListComponent } from './features/devis/devis-list/devis-list.component';
import { DevisFormComponent } from './features/devis/devis-form/devis-form.component';
import { FactureListComponent } from './features/factures/facture-list/facture-list.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },

  {
    path: 'clients',
    canActivate: [AuthGuard],
    children: [
      { path: '', component: ClientListComponent },
      { path: 'nouveau', component: ClientFormComponent },
      { path: ':id/modifier', component: ClientFormComponent },
    ],
  },
  {
    path: 'produits',
    canActivate: [AuthGuard],
    children: [
      { path: '', component: ProduitListComponent },
      { path: 'nouveau', component: ProduitFormComponent },
      { path: ':id/modifier', component: ProduitFormComponent },
    ],
  },

  // --- NOUVEAU : Devis ---
  {
    path: 'devis',
    canActivate: [AuthGuard],
    children: [
      { path: '', component: DevisListComponent },
      { path: 'nouveau', component: DevisFormComponent },
      // Note : On ne modifie pas un devis validé, mais pour l'instant on garde la route
      { path: ':id/modifier', component: DevisFormComponent },
    ],
  },
  {
    path: 'factures',
    component: FactureListComponent,
    canActivate: [AuthGuard],
  },

  { path: '**', redirectTo: '/login' },
];
