import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientService } from '../../../core/services/client.service';
import { Client } from '../../../core/models/api-models';

@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './client-form.component.html',
})
export class ClientFormComponent implements OnInit {
  clientForm: FormGroup;
  isEditMode = false;
  clientId?: number;

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.clientForm = this.fb.group({
      nom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    // Vérifier si on est en mode modification (présence d'un ID dans l'URL)
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.clientId = +id;
      this.loadClient(this.clientId);
    }
  }

  loadClient(id: number): void {
    this.clientService.getById(id).subscribe((client) => {
      this.clientForm.patchValue(client); // Remplit le formulaire
    });
  }

  onSubmit(): void {
    if (this.clientForm.invalid) return;

    const clientData: Client = this.clientForm.value;

    if (this.isEditMode && this.clientId) {
      this.clientService.update(this.clientId, clientData).subscribe(() => {
        this.router.navigate(['/clients']);
      });
    } else {
      this.clientService.create(clientData).subscribe(() => {
        this.router.navigate(['/clients']);
      });
    }
  }
}
