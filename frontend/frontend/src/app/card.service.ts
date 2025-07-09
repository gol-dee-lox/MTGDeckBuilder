import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Make this match exactly what your backend Jackson mapper
 * is putting into each Card JSON:
 */
export interface Card {
  // identity
  uuid: string;
  name: string;
  fName: string;
  
  // mana / cost / value
  mCost: string;
  mVal: string;

  // stats
  power: string;
  toughness: string;

  // text
  text: string;
  fText: string;
  keywords: string[];

  // sets & codes
  setCode: string;

  // types
  types: string[];
  subtypes: string[];
  supertypes: string[];

  // colors
  colors: string[];
  colorIdentity: string[];

  // format legalities (all strings: “LEGAL”, “BANNED”, “”, etc.)
  alchemy: string;
  brawl: string;
  cap: string;
  commander: string;
  duel: string;
  edhrR: string;
  edhrS: string;
  future: string;
  gladiator: string;
  historic: string;
  lead: string;
  legacy: string;
  loyal: string;
  modern: string;
  oathbreaker: string;
  oldschool: string;
  pauper: string;
  paupercommander: string;
  penny: string;
  pioneer: string;
  predh: string;
  premodern: string;
  rare: string;
  standard: string;
  standardbrawl: string;
  timeless: string;
  vintage: string;

  // in case you add more later
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