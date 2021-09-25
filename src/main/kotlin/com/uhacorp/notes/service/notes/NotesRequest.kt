package com.uhacorp.notes.service.notes

import com.fasterxml.jackson.annotation.JsonProperty

class NotesRequest(
    @JsonProperty("id")
    val id: Long? = 0,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("notes")
    val notes: String
)