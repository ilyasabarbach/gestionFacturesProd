import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProduitService } from '../../../core/services/produit.service';
import { Produit } from '../../../core/models/api-models';

@Component({
  selector: 'app-produit-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './produit-list.component.html',
})
export class ProduitListComponent implements OnInit {
  produits: Produit[] = [];
  filteredProduits: Produit[] = [];
  searchTerm: string = '';

  constructor(private produitService: ProduitService) {}

  ngOnInit(): void {
    this.loadProduits();
  }

  loadProduits(): void {
    this.produitService.getAll().subscribe({
      next: (data) => {
        this.produits = data;
        this.filterProduits(); // Appliquer le filtre si existant
      },
      error: (err) => console.error('Erreur chargement produits', err),
    });
  }

  deleteProduit(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce produit ?')) {
      this.produitService.delete(id).subscribe(() => this.loadProduits());
    }
  }

  // Méthodes rapides pour le stock
  ajusterStock(produit: Produit, quantite: number, isAjout: boolean): void {
    if (!isAjout && produit.quantiteStock <= 0) return; // Pas de stock négatif

    const obs = isAjout
      ? this.produitService.ajouterStock(produit.id!, quantite)
      : this.produitService.reduireStock(produit.id!, quantite);

    obs.subscribe({
      next: (updatedProduit) => {
        // Mise à jour locale pour éviter de recharger toute la liste
        produit.quantiteStock = updatedProduit.quantiteStock;
      },
      error: (err) =>
        alert('Erreur lors de la mise à jour du stock : ' + err.message),
    });
  }

  filterProduits(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredProduits = this.produits.filter(
      (p) =>
        p.nom.toLowerCase().includes(term) ||
        p.categorie.toLowerCase().includes(term)
    );
  }
}
