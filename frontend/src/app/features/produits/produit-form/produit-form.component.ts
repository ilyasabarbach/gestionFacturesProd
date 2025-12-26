import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProduitService } from '../../../core/services/produit.service';
import { Produit } from '../../../core/models/api-models';

@Component({
  selector: 'app-produit-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './produit-form.component.html',
})
export class ProduitFormComponent implements OnInit {
  produitForm: FormGroup;
  isEditMode = false;
  produitId?: number;

  constructor(
    private fb: FormBuilder,
    private produitService: ProduitService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.produitForm = this.fb.group({
      nom: ['', Validators.required],
      description: [''],
      prix: [0, [Validators.required, Validators.min(0)]],
      quantiteStock: [0, [Validators.required, Validators.min(0)]],
      categorie: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.produitId = +id;
      this.produitService.getById(this.produitId).subscribe((data) => {
        this.produitForm.patchValue(data);
      });
    }
  }

  onSubmit(): void {
    if (this.produitForm.invalid) return;

    const produitData: Produit = this.produitForm.value;

    if (this.isEditMode && this.produitId) {
      this.produitService.update(this.produitId, produitData).subscribe(() => {
        this.router.navigate(['/produits']);
      });
    } else {
      this.produitService.create(produitData).subscribe(() => {
        this.router.navigate(['/produits']);
      });
    }
  }
}
