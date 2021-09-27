package com.uhacorp.notes.service.notes

import com.uhacorp.notes.exception.AppException
import com.uhacorp.notes.exception.ExceptionType
import com.uhacorp.notes.mapper.toNotes
import com.uhacorp.notes.model.EntityType
import com.uhacorp.notes.model.Notes
import com.uhacorp.notes.repository.NotesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


interface NotesService {
    fun createNotes(request: NotesRequest): Notes?
    fun getAllNotes(): List<Notes>
    fun updateNotes(request: NotesRequest): Notes?
    fun deleteNotes(id: Long): Boolean
}

@Component
class NotesServiceImpl : NotesService {

    @Autowired
    private lateinit var notesRepository: NotesRepository

    @Autowired
    private lateinit var appException: AppException

    override fun createNotes(request: NotesRequest): Notes? {
        return try {
            val notes = request.toNotes()
            return notesRepository.save(notes)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun getAllNotes(): List<Notes> {
        return notesRepository.findAll().toList()
    }

    override fun updateNotes(request: NotesRequest): Notes? {
        val isNotesExist = notesRepository.existsById(request.id ?: 0L)
        if (isNotesExist) {
            val notes = request.toNotes()
            notesRepository.save(notes)
            return notes
        } else {
            throw exception(EntityType.NOTES, ExceptionType.ENTITY_NOT_FOUND, "Notes not existing")
        }
    }

    override fun deleteNotes(id: Long): Boolean {
        return try {
            notesRepository.deleteById(id)
            true
        } catch(e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private fun exception(entityType: EntityType, exceptionType: ExceptionType, vararg args: String): RuntimeException {
        return appException.throwException(entityType, exceptionType, *args)
    }
}