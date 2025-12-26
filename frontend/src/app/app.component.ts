import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
// Importez le NavbarComponent
import { NavbarComponent } from './shared/layout/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  // Ajoutez NavbarComponent dans les imports
  imports: [CommonModule, RouterOutlet, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'gestion-factures';
}
