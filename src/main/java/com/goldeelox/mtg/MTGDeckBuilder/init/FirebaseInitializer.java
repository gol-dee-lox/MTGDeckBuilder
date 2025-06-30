package com.goldeelox.mtg.MTGDeckBuilder.init;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Component
public class FirebaseInitializer {

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("Firebase initialized.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }
}