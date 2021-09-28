package com.uhacorp.notes.service.user

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRequest(

    @JsonProperty("id")
    val id: Long? = 0,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("birth_day")
    val birthDay: String,

    @JsonProperty("profile_picture")
    val profilePicture: String,

    @JsonProperty("password")
    val password: String
)