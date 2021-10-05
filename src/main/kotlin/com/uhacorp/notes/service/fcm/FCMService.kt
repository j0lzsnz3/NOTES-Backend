package com.uhacorp.notes.service.fcm

import com.google.firebase.messaging.*
import com.google.gson.GsonBuilder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.ExecutionException


@Service
class FCMService {

    private val logger by lazy { LoggerFactory.getLogger(FCMService::class.java) }

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessage(data: Map<String, String>, request: PushNotificationRequest) {
        val message = getPreconfiguredMessageWithData(data, request)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonOutput = gson.toJson(message)
        val response = sendAndGetResponse(message)
        logger.info("Sent message with data. Topic: " + request.topic + ", " + response + " msg " + jsonOutput)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessageWithoutData(request: PushNotificationRequest) {
        val message = getPreconfiguredMessageWithoutData(request)
        val response = sendAndGetResponse(message)
        logger.info("Sent message without data. Topic: " + request.topic + ", " + response)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessageToToken(request: PushNotificationRequest) {
        val message = getPreconfiguredMessageToToken(request)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonOutput = gson.toJson(message)
        val response = sendAndGetResponse(message)
        logger.info("Sent message to token. Device token: " + request.token + ", " + response + " msg " + jsonOutput)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendAndGetResponse(message: Message): String {
        return FirebaseMessaging.getInstance().sendAsync(message).get()
    }

    fun getAndroidConfig(topic: String): AndroidConfig {
        return AndroidConfig.builder()
            .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
            .setPriority(AndroidConfig.Priority.HIGH)
            .setNotification(
                AndroidNotification.builder().setSound(NotificationParameter.SOUND.value)
                    .setColor(NotificationParameter.COLOR.value).setTag(topic).build()
            ).build()
    }

    fun getApnsConfig(topic: String): ApnsConfig {
        return ApnsConfig.builder()
            .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build()
    }

    fun getPreconfiguredMessageToToken(request: PushNotificationRequest): Message {
        return getPreconfiguredMessageBuilder(request).setToken(request.token)
            .build()
    }

    fun getPreconfiguredMessageWithoutData(request: PushNotificationRequest): Message {
        return getPreconfiguredMessageBuilder(request).setTopic(request.topic)
            .build()
    }

    fun getPreconfiguredMessageWithData(
        data: Map<String, String>,
        request: PushNotificationRequest
    ): Message {
        return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.token)
            .build()
    }

    fun getPreconfiguredMessageBuilder(request: PushNotificationRequest): Message.Builder {
        val androidConfig = getAndroidConfig(request.topic)
        val apnsConfig = getApnsConfig(request.topic)
        val notification = Notification.builder()
            .setTitle(request.title)
            .setBody(request.message)
            .build()

        return Message.builder()
            .setApnsConfig(apnsConfig)
            .setAndroidConfig(androidConfig)
            .setNotification(notification)
    }

}