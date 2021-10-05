package com.uhacorp.notes.service.fcm

import com.fasterxml.jackson.annotation.JsonProperty

data class PushNotificationResponse(
    @JsonProperty("status")
    val status: Int,

    @JsonProperty("message")
    val message: String
)