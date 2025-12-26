import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FactureService } from '../../../core/services/facture.service';
import { Facture } from '../../../core/models/api-models';

@Component({
  selector: 'app-facture-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './facture-list.component.html',
})
export class FactureListComponent implements OnInit {
  factures: Facture[] = [];

  constructor(private factureService: FactureService) {}

  ngOnInit(): void {
    this.factureService.getAll().subscribe({
      next: (data) => (this.factures = data),
      error: (err) => console.error(err),
    });
  }

  imprimer(facture: Facture): void {
    const popupWin = window.open('', '_blank', 'width=800,height=600');

    if (!popupWin) {
      alert('Veuillez autoriser les popups pour imprimer.');
      return;
    }

    const htmlContent = `
      <html>
        <head>
          <title>Facture ${facture.numeroFacture}</title>
          <style>
            body { font-family: Arial, sans-serif; padding: 20px; }
            .header { text-align: center; margin-bottom: 40px; border-bottom: 2px solid #333; padding-bottom: 20px; }
            .details { margin-bottom: 30px; }
            .details-table { width: 100%; border-collapse: collapse; }
            .details-table td { padding: 8px; }
            .label { font-weight: bold; width: 150px; }
            .total { font-size: 1.5em; font-weight: bold; color: #28a745; text-align: right; margin-top: 20px; }
            .footer { margin-top: 50px; font-size: 0.8em; text-align: center; color: #666; }
          </style>
        </head>
        <body>
          <div class="header">
            <h1>FACTURE</h1>
            <h3>${facture.numeroFacture}</h3>
          </div>

          <div class="details">
            <table class="details-table">
              <tr>
                <td class="label">Date :</td>
                <td>${facture.dateFacture}</td>
              </tr>
              <tr>
                <td class="label">Client :</td>
                <td>${facture.nomClient}</td>
              </tr>
              <tr>
                <td class="label">Référence Devis :</td>
                <td>${facture.referenceDevis}</td>
              </tr>
            </table>
          </div>

          <div class="total">
            Total à payer : ${facture.montantTTC} €
          </div>

          <div class="footer">
            Merci de votre confiance. <br>
            Société GestionFactures - 123 Rue de l'Innovation
          </div>
          
          <script>
            window.onload = function() { window.print(); window.close(); }
          </script>
        </body>
      </html>
    `;

    popupWin.document.open();
    popupWin.document.write(htmlContent);
    popupWin.document.close();
  }
}
