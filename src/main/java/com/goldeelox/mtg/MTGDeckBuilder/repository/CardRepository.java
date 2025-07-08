package com.goldeelox.mtg.MTGDeckBuilder.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.goldeelox.mtg.MTGDeckBuilder.model.Card;

@Component
public class CardRepository {

    private final List<Card> cards = new ArrayList<>();

    public List<Card> getAllCards() {
        return cards;
    }

    public void setCards(List<Card> newCards) {
        cards.clear();
        cards.addAll(newCards);
        System.out.println("📦 Returning cards: " + newCards.size());
    }
}