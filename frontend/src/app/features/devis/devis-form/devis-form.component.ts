import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  FormArray,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { DevisService } from '../../../core/services/devis.service';
import { ClientService } from '../../../core/services/client.service';
import { ProduitService } from '../../../core/services/produit.service';
import { Client, Produit } from '../../../core/models/api-models';

@Component({
  selector: 'app-devis-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './devis-form.component.html',
})
export class DevisFormComponent implements OnInit {
  devisForm: FormGroup;
  clients: Client[] = [];
  produits: Produit[] = [];

  constructor(
    private fb: FormBuilder,
    private devisService: DevisService,
    private clientService: ClientService,
    private produitService: ProduitService,
    private router: Router
  ) {
    // Initialisation du formulaire
    this.devisForm = this.fb.group({
      numeroDevis: [
        `DEV-${new Date().getFullYear()}-${Math.floor(Math.random() * 1000)}`,
        Validators.required,
      ],
      dateEmission: [
        new Date().toISOString().substring(0, 10),
        Validators.required,
      ],
      clientId: ['', Validators.required],
      lignes: this.fb.array([]), // Liste dynamique
    });
  }

  ngOnInit(): void {
    // Charger les listes déroulantes
    this.clientService.getAll().subscribe((data) => (this.clients = data));
    this.produitService.getAll().subscribe((data) => (this.produits = data));

    // Ajouter une ligne vide par défaut
    this.ajouterLigne();
  }

  // Getter pour accéder facilement au FormArray dans le HTML
  get lignes(): FormArray {
    return this.devisForm.get('lignes') as FormArray;
  }

  // Créer une nouvelle ligne de produit
  ajouterLigne(): void {
    const ligneGroup = this.fb.group({
      produitId: ['', Validators.required],
      quantite: [1, [Validators.required, Validators.min(1)]],
    });
    this.lignes.push(ligneGroup);
  }

  supprimerLigne(index: number): void {
    this.lignes.removeAt(index);
  }

  onSubmit(): void {
    if (this.devisForm.invalid) return;

    // Le backend attend "clientId" et un tableau "lignes"
    const formData = this.devisForm.value;

    this.devisService.create(formData).subscribe({
      next: () => this.router.navigate(['/devis']),
      error: (err) =>
        alert(
          'Erreur lors de la création : ' + (err.error?.message || err.message)
        ),
    });
  }
}
