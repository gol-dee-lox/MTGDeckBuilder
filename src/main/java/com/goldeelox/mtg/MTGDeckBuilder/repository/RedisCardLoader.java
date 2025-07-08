package com.goldeelox.mtg.MTGDeckBuilder.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldeelox.mtg.MTGDeckBuilder.model.Card;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RedisCardLoader {

    private static final int CHUNK_SIZE = 2000;
    
    @Autowired
    @Qualifier("directLettuceFactory")
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CardRepository cardRepository;

    @PostConstruct
    public void loadCardsIntoMemory() {
        try {
            System.out.println("✅ Starting Redis SCAN using secure connection...");
            ScanOptions options = ScanOptions.scanOptions().match("*").count(CHUNK_SIZE).build();

            @SuppressWarnings("deprecation")
			Cursor<byte[]> cursor = redisConnectionFactory.getConnection().scan(options);

            List<String> allKeys = new ArrayList<>();
            while (cursor.hasNext()) {
                String key = new String(cursor.next());
                allKeys.add(key);
            }

            if (allKeys.isEmpty()) {
                System.out.println("⚠️ No keys found in Redis via SCAN.");
                return;
            }

            System.out.println("✅ Total keys found: " + allKeys.size());

            List<Card> loadedCards = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();

            for (int i = 0; i < allKeys.size(); i += CHUNK_SIZE) {
                int end = Math.min(i + CHUNK_SIZE, allKeys.size());
                List<String> chunk = allKeys.subList(i, end);

                List<String> jsonResults = redisTemplate.opsForValue().multiGet(chunk);

                if (jsonResults != null) {
                    for (int j = 0; j < chunk.size(); j++) {
                        String key = chunk.get(j);
                        String json = jsonResults.get(j);
                        if (json != null && !json.isBlank()) {
                            try {
                                Card card = mapper.readValue(json, Card.class);
                                loadedCards.add(card);
                            } catch (JsonProcessingException e) {
                                System.err.println("❌ Failed to parse JSON for key: " + key);
                                System.err.println("❌ Error: " + e.getMessage());
                            }
                        } else {
                            System.out.println("⚠️ Empty or null value for key: " + key);
                        }
                    }
                }
            }

            cardRepository.setCards(loadedCards);
            System.out.println("✅ Loaded " + loadedCards.size() + " cards from Redis into memory.");
        } catch (Exception e) {
            System.err.println("❗ RedisCardLoader failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
