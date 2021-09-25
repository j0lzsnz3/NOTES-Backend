package com.uhacorp.notes.repository

import com.uhacorp.notes.model.Notes
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

interface NotesRepository : CrudRepository<Notes, Long> {
}