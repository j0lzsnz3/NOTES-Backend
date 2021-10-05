package com.uhacorp.notes.service.fcm

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class FCMInitializer {
    @Value("\${app.firebase-configuration-file}")
    private val firebaseConfigPath: String = ""

    private val logger by lazy { LoggerFactory.getLogger(FCMInitializer::class.java) }

    @PostConstruct
    fun initialize() {
        try {
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(ClassPathResource(firebaseConfigPath).inputStream))
                .build()

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
                logger.info("Firebase app has been initialized")
            } else {
                logger.info("Firebase app failed to initialize")
            }
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }
}