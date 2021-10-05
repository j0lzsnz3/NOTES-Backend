package com.uhacorp.notes.service.fcm

import com.fasterxml.jackson.annotation.JsonProperty


data class PushNotificationRequest(
    @JsonProperty("title")
    val title: String,

    @JsonProperty("message")
    val message: String,

    @JsonProperty("topic")
    val topic: String,

    @JsonProperty("token")
    val token: String
)