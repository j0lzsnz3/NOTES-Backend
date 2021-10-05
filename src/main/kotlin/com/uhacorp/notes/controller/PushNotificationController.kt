package com.uhacorp.notes.controller

import com.uhacorp.notes.service.fcm.PushNotificationRequest
import com.uhacorp.notes.service.fcm.PushNotificationResponse
import com.uhacorp.notes.service.fcm.PushNotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/notification")
class PushNotificationController {

    @Autowired
    private lateinit var pushNotificationService: PushNotificationService

    /**
     * a) Push notification based on topics.
     * In this kind of notification we don’t required any device token.
     * So based on topic configuration push notification will work.
     */
    @PostMapping("/topic")
    fun sendNotification(@RequestBody request: PushNotificationRequest?): ResponseEntity<*>? {
        pushNotificationService.sendPushNotificationWithoutData(request)
        return ResponseEntity(
            PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
            HttpStatus.OK
        )
    }

    /**
     * Push notification with token.
     * With this end point we can send only title and body part.
     */
    @PostMapping("/token")
    fun sendTokenNotification(@RequestBody request: PushNotificationRequest?): ResponseEntity<*>? {
        pushNotificationService.sendPushNotificationToToken(request)
        return ResponseEntity(
            PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
            HttpStatus.OK
        )
    }

    /**
     * Push notification with additional payload data.
     * With this end point we can send title, body and some additional key/pair data.
     */
    @PostMapping("/data")
    fun sendDataNotification(@RequestBody request: PushNotificationRequest?): ResponseEntity<*>? {
        pushNotificationService.sendPushNotification(request)
        return ResponseEntity(
            PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
            HttpStatus.OK
        )
    }
}