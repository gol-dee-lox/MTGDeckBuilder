import { Component, OnInit } from '@angular/core';
import { CommonModule, NgClass, TitleCasePipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Card, CardService } from './card.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,     // for *ngIf, *ngFor
    NgClass,          // for [ngClass]
    TitleCasePipe,    // for | titlecase
    HttpClientModule  // for injecting HttpClient in CardService
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.scss']
})
export class AppComponent implements OnInit {
  card: Card | null = null;
  error: string | null = null;

  formats: string[] = [
    'standard','standardbrawl','pioneer','modern','legacy','vintage',
    'pauper','commander','brawl','historic','oathbreaker','alchemy',
    'duel','edhrr','edhrs','future','gladiator','lead','loyal',
    'oldschool','paupercommander','penny','predh','premodern'
  ];

  constructor(private cardService: CardService) {}

  ngOnInit(): void {
    this.loadRandomCard();
  }

  loadRandomCard(): void {
    this.cardService.getAllCards().subscribe({
      next: (cards: Card[]) => {
        const idx = Math.floor(Math.random() * cards.length);
        this.card = cards[idx];
      },
      error: (err: any) => {
        this.error = err.message || 'Failed to load card';
      }
    });
  }
}