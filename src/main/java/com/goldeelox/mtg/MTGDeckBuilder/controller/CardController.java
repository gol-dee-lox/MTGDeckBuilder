package com.goldeelox.mtg.MTGDeckBuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldeelox.mtg.MTGDeckBuilder.model.Card;
import com.goldeelox.mtg.MTGDeckBuilder.repository.CardRepository;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @GetMapping
    public List<Card> getAllCards() {
        return cardRepository.getAllCards();
    }
}