import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CardService, Card } from './card.service';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.html',
  imports: [CommonModule, HttpClientModule],
})
export class AppComponent implements OnInit {
  card?: Card;
  error = '';

  constructor(private cardService: CardService) {}

  ngOnInit() {
    console.log('AppComponent initialized');
    this.cardService.getAllCards().subscribe({
      next: (cards) => {
        const random = Math.floor(Math.random() * cards.length);
        this.card = cards[random];
      },
      error: (err) => {
        console.error('Error loading cards:', err);
        this.error = 'Could not load card data.';
      },
    });
  }
}