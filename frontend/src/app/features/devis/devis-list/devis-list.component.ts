import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DevisService } from '../../../core/services/devis.service';
import { Devis, StatutDevis } from '../../../core/models/api-models';

@Component({
  selector: 'app-devis-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './devis-list.component.html',
})
export class DevisListComponent implements OnInit {
  devisList: Devis[] = [];

  constructor(private devisService: DevisService) {}

  ngOnInit(): void {
    this.loadDevis();
  }

  loadDevis(): void {
    this.devisService.getAll().subscribe({
      next: (data) => (this.devisList = data),
      error: (err) => console.error(err),
    });
  }

  validerDevis(id: number): void {
    if (confirm('Confirmer la validation de ce devis ?')) {
      this.devisService.valider(id).subscribe(() => this.loadDevis());
    }
  }

  creerFacture(id: number): void {
    if (confirm('Générer la facture pour ce devis ?')) {
      this.devisService.facturer(id).subscribe({
        next: () => {
          alert('Facture générée avec succès !');
        },
        error: (err) => {
          // CORRECTION ICI : On cherche 'error' et non 'message'
          // On affiche aussi un fallback si jamais c'est vide
          const message =
            err.error?.error || 'Une erreur inconnue est survenue';
          alert('Erreur : ' + message);
          console.error(err);
        },
      });
    }
  }

  deleteDevis(id: number): void {
    if (confirm('Supprimer ce devis ?')) {
      this.devisService.delete(id).subscribe(() => this.loadDevis());
    }
  }

  // Helper pour les classes CSS des badges
  getBadgeClass(statut: StatutDevis): string {
    switch (statut) {
      case StatutDevis.BROUILLON:
        return 'bg-secondary';
      case StatutDevis.ENVOYE:
        return 'bg-info';
      case StatutDevis.ACCEPTE:
        return 'bg-success';
      case StatutDevis.REFUSE:
        return 'bg-danger';
      default:
        return 'bg-light text-dark';
    }
  }
}
