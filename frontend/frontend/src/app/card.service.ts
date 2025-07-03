import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Card {
  name: string;
  colors: string[];
  power: string;
  toughness: string;
  text: string;
  [key: string]: any;
}

@Injectable({ providedIn: 'root' })
export class CardService {
  private backendUrl = 'https://mtgdeckbuilder-ewid.onrender.com';

  constructor(private http: HttpClient) {}

  getAllCards(): Observable<Card[]> {
    return this.http.get<Card[]>(`${this.backendUrl}/cards`, { withCredentials: true });
  }
}