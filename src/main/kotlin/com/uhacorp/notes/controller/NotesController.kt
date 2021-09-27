package com.uhacorp.notes.controller

import com.uhacorp.notes.model.Notes
import com.uhacorp.notes.service.GeneralResponse
import com.uhacorp.notes.service.ResponseError
import com.uhacorp.notes.service.notes.NotesRequest
import com.uhacorp.notes.service.notes.NotesService
import com.uhacorp.notes.util.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/notes")
class NotesController {

    @Autowired
    private lateinit var notesService: NotesService

    @PostMapping("/create")
    fun createNotes(@RequestBody request: NotesRequest): ResponseEntity<Any> {
        val notes = notesService.createNotes(request)
        return if (notes != null) {
            ResponseEntity(notes, HttpStatus.OK)
        } else {
            ResponseEntity(
                ResponseError(
                    timestamp = DateUtils.today(),
                    status = HttpStatus.EXPECTATION_FAILED.value().toString(),
                    error = "Internal Error: Failed to create notes",
                    path = "notes/create"
                ),
                HttpStatus.EXPECTATION_FAILED
            )
        }
    }

    @PostMapping("/update")
    fun updateNotes(@RequestBody request: NotesRequest): ResponseEntity<Any> {
        val notes = notesService.updateNotes(request)
        return if (notes != null) {
            ResponseEntity(notes, HttpStatus.OK)
        } else {
            ResponseEntity(
                ResponseError(
                    timestamp = DateUtils.today(),
                    status = HttpStatus.NOT_FOUND.value().toString(),
                    error = "User not found",
                    path = "notes/update"
                ),
                HttpStatus.NOT_FOUND
            )
        }
    }

    @GetMapping("/all")
    fun getAllNotes(): ResponseEntity<List<Notes>> {
        val listNotes = notesService.getAllNotes()
        return ResponseEntity(listNotes, HttpStatus.OK)
    }

    @PostMapping("/delete")
    fun deleteNotes(@RequestParam("notes_id") id: Long): ResponseEntity<Any> {
        notesService.deleteNotes(id)
        return ResponseEntity(GeneralResponse("Delete success"), HttpStatus.OK)
    }

}