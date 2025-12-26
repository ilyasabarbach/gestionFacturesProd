// Réponse d'authentification
export interface AuthResponse {
  token: string;
}

// Pour le Login
export interface LoginRequest {
  email: string;
  password: string;
}

// Pour l'inscription
export interface RegisterRequest {
  email: string;
  password: string;
  role: 'ADMIN' | 'USER';
}

// --- CLIENTS ---
export interface Client {
  id?: number; // Optionnel car absent à la création
  nom: string;
  email: string;
  telephone: string;
}

// --- PRODUITS ---
export interface Produit {
  id?: number;
  nom: string;
  description?: string;
  prix: number;
  quantiteStock: number;
  categorie: string;
}

// --- DEVIS & FACTURES ---

export enum StatutDevis {
  BROUILLON = 'BROUILLON',
  ENVOYE = 'ENVOYE',
  ACCEPTE = 'ACCEPTE',
  REFUSE = 'REFUSE',
}

export interface LigneDevis {
  id?: number;
  produitId: number;
  nomProduit?: string; // Utile pour l'affichage
  quantite: number;
  prixUnitaire?: number; // Le back renvoie ça parfois
}

export interface Devis {
  id?: number;
  numeroDevis: string;
  dateEmission: string; // Format ISO date 'YYYY-MM-DD'
  statut: StatutDevis;
  clientId: number;
  nomClient?: string; // Pour affichage facile dans la liste
  lignes: LigneDevis[];
  totalHT: number;
  totalTVA: number;
  totalTTC: number;
}

export interface Facture {
  id: number;
  numeroFacture: string;
  dateFacture: string;
  montantTTC: number;

  // CORRECTION : On matche le DTO du Backend
  nomClient: string;
  referenceDevis: string;

  // On supprime "client: Client" car le backend envoie maintenant juste le nom
}
