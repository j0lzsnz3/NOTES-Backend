package com.uhacorp.notes.model

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.NoArgsConstructor
import javax.persistence.*

@NoArgsConstructor
@Entity
@Table(name = "notes")
class Notes(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    val id: Long? = 0,

    @Column(name = "title")
    @JsonProperty("title")
    val title: String,

    @Column(name = "notes")
    @JsonProperty("notes")
    val notes: String
)