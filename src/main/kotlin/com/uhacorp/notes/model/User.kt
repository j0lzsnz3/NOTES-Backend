package com.uhacorp.notes.model

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.NoArgsConstructor
import javax.persistence.*

@NoArgsConstructor
@Entity
@Table(name = "user")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    val id: Long? = 0,

    @Column(name = "name")
    @JsonProperty("name")
    val name: String,

    @Column(name = "birth_day")
    @JsonProperty("birth_day")
    val birthDay: String,

    @Column(name = "email")
    @JsonProperty("email")
    val email: String,

    @Column(name = "profile_picture")
    @JsonProperty("profile_picture")
    val profilePicture: String,

    @Column(name = "password")
    @JsonProperty("password")
    val password: String
)