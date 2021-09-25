package com.uhacorp.notes.mapper

import com.uhacorp.notes.model.Notes
import com.uhacorp.notes.service.notes.NotesRequest

fun NotesRequest.toNotes(): Notes {
    return Notes(
        id = this.id,
        title = this.title,
        notes = this.notes
    )
}