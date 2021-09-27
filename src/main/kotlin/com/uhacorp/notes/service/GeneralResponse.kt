package com.uhacorp.notes.service

import com.fasterxml.jackson.annotation.JsonProperty

data class GeneralResponse(
    @JsonProperty("message")
    val message: String
)