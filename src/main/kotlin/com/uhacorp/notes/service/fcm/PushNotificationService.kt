package com.uhacorp.notes.service.fcm

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class PushNotificationService {

    @Autowired
    private lateinit var fcmService: FCMService

    private val logger by lazy { LoggerFactory.getLogger(PushNotificationService::class.java) }

    fun sendPushNotification(request: PushNotificationRequest?) {
        try {
            fcmService.sendMessage(getSamplePayloadData()!!, request!!)
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }

    fun sendPushNotificationWithoutData(request: PushNotificationRequest?) {
        try {
            fcmService.sendMessageWithoutData(request!!)
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }


    fun sendPushNotificationToToken(request: PushNotificationRequest?) {
        try {
            fcmService.sendMessageToToken(request!!)
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }


    fun getSamplePayloadData(): Map<String, String>? {
        val pushData: MutableMap<String, String> = HashMap()
        pushData["messageId"] = "msgid"
        pushData["text"] = "payload notification example"
        pushData["user"] = "imam kurniansyah"
        return pushData
    }
}