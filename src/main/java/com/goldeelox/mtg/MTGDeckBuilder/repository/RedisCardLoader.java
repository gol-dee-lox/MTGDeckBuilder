package com.goldeelox.mtg.MTGDeckBuilder.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
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

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RedisCardLoader {

    @Autowired
    @Qualifier("directLettuceFactory")
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CardRepository cardRepository;

    @PostConstruct
    public void loadCardsIntoMemory() {
        System.out.println("✅ Starting Redis SCAN using secure connection...");
        ScanOptions options = ScanOptions.scanOptions()
                                        .match("*")
                                        .count(2000)
                                        .build();

        @SuppressWarnings("deprecation")
        Cursor<byte[]> cursor = redisConnectionFactory
                                    .getConnection()
                                    .scan(options);

        List<String> allKeys = new ArrayList<>();
        while (cursor.hasNext()) {
            allKeys.add(new String(cursor.next(), StandardCharsets.UTF_8));
        }

        if (allKeys.isEmpty()) {
            System.out.println("⚠️ No keys found in Redis via SCAN.");
            return;
        }

        System.out.println("✅ Total keys found: " + allKeys.size());

        List<Card> loadedCards = new ArrayList<>();

        // Case-insensitive binding: lowercase redis fields → camelCase Java props
        @SuppressWarnings("deprecation")
		ObjectMapper mapper = new ObjectMapper()
        	    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        	    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Fields that need comma-splitting (after stripping JSON array syntax)
        Set<String> commaSplitFields = Set.of(
            "colors", "coloridentity",
            "types", "subtypes", "supertypes"
        );
        // Field that needs tilde-splitting
        String keywordField = "keywords";

        for (String key : allKeys) {
            Map<Object, Object> raw = redisTemplate.opsForHash().entries(key);
            if (raw == null || raw.isEmpty()) {
                System.out.println("⚠️ No hash entries for key: " + key);
                continue;
            }

            // Cast to Map<String,String> since we store only strings
            @SuppressWarnings("unchecked")
            Map<String,String> stringHash = (Map<String,String>)(Map<?,?>) raw;

            // Build normalized map: fieldName -> String or List<String>
            Map<String,Object> normalized = new LinkedHashMap<>();
            for (Map.Entry<String,String> entry : stringHash.entrySet()) {
                String field = entry.getKey().toLowerCase(Locale.ROOT);
                String val   = entry.getValue();

                if (val == null || val.isBlank()) {
                    // skip null/blank entirely or replace with empty
                    continue;
                }

                if (keywordField.equals(field)) {
                    // split on tilde
                    List<String> parts = Arrays.stream(val.split("~", -1))
                                               .map(String::trim)
                                               .filter(s -> !s.isEmpty())
                                               .collect(Collectors.toList());
                    normalized.put(field, parts);

                } else if (commaSplitFields.contains(field)) {
                    // strip JSON array syntax then split on comma
                    String clean = val.replaceAll("[\\[\\]\"]", "");
                    List<String> parts = Arrays.stream(clean.split(",", -1))
                                               .map(String::trim)
                                               .filter(s -> !s.isEmpty())
                                               .collect(Collectors.toList());
                    normalized.put(field, parts);

                } else {
                    // single string field
                    normalized.put(field, val.trim());
                }
            }

            try {
                Card card = mapper.convertValue(normalized, Card.class);
                loadedCards.add(card);
            } catch (IllegalArgumentException ex) {
                System.err.println("❌ Failed to map hash to Card for key: " + key);
                System.err.println("❌ Error: " + ex.getMessage());
            }
        }

        cardRepository.setCards(loadedCards);
        System.out.println("✅ Loaded " + loadedCards.size() + " cards from Redis into memory.");
    }
}