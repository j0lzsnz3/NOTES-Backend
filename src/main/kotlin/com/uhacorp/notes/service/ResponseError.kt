package com.uhacorp.notes.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import lombok.experimental.Accessors
import java.util.*

@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseError(
    val timestamp: Date,
    val message: String,
    val detail: String
)