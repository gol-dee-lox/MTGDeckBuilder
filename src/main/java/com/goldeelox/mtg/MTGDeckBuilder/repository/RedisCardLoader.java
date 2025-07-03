package com.goldeelox.mtg.MTGDeckBuilder.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldeelox.mtg.MTGDeckBuilder.model.Card;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RedisCardLoader {

    private static final int CHUNK_SIZE = 200; // adjust as needed for 10MB safety

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CardRepository cardRepository;

    @PostConstruct
    public void loadCardsIntoMemory() {
    	try {
	        Set<String> allKeys = redisTemplate.keys("*");
	        if (allKeys == null || allKeys.isEmpty()) {
	            System.out.println("⚠️ No keys found in Redis.");
	            return;
	        }
	
	        List<Card> loadedCards = new ArrayList<>();
	        ObjectMapper mapper = new ObjectMapper();
	
	        List<String> keysList = new ArrayList<>(allKeys);
	        for (int i = 0; i < keysList.size(); i += CHUNK_SIZE) {
	            int end = Math.min(i + CHUNK_SIZE, keysList.size());
	            List<String> chunk = keysList.subList(i, end);
	
	            List<String> jsonResults = redisTemplate.opsForValue().multiGet(chunk);
	
	            if (jsonResults != null) {
	                for (String json : jsonResults) {
	                    if (json != null && !json.isBlank()) {
	                        try {
	                            Card card = mapper.readValue(json, Card.class);
	                            loadedCards.add(card);
	                        } catch (JsonProcessingException e) {
	                            System.err.println("❌ Error parsing JSON for card: " + e.getMessage());
	                        }
	                    }
	                }
	            }
	        }
	
	        cardRepository.setCards(loadedCards);
	        System.out.println("✅ Loaded " + loadedCards.size() + " cards from Redis into memory.");
    	} catch (Exception e) {
            System.err.println("❗ RedisCardLoader failed: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace(); // <-- Very important
        }
    }
}
