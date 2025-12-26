import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Devis } from '../models/api-models';

@Injectable({
  providedIn: 'root',
})
export class DevisService {
  private apiUrl = 'http://localhost:8080/api/devis';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Devis[]> {
    return this.http.get<Devis[]>(this.apiUrl);
  }

  getById(id: number): Observable<Devis> {
    return this.http.get<Devis>(`${this.apiUrl}/${id}`);
  }

  create(devis: Devis): Observable<Devis> {
    return this.http.post<Devis>(this.apiUrl, devis);
  }

  // Pas de update complet dans ce tuto pour simplifier (car complexe avec les lignes)
  // Mais vous pouvez l'ajouter si besoin.

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // --- Actions Métier ---

  valider(id: number): Observable<Devis> {
    return this.http.patch<Devis>(`${this.apiUrl}/${id}/valider`, {});
  }

  facturer(id: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${id}/facturer`, {});
  }
}
