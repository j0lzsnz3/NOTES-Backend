package com.uhacorp.notes.controller

import com.uhacorp.notes.model.Notes
import com.uhacorp.notes.service.notes.NotesRequest
import com.uhacorp.notes.service.notes.NotesService
import com.uhacorp.notes.service.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/notes")
class NotesController {

    @Autowired
    private lateinit var notesService: NotesService

    @PostMapping("/create")
    fun createNotes(@RequestBody request: NotesRequest): Response<Notes> {
        val notes = notesService.createNotes(request)
        return if (notes != null) {
            Response<Notes>().ok(notes)
        } else {
            Response<Notes>().notFound()
        }
    }

    @PostMapping("/update")
    fun updateNotes(@RequestBody request: NotesRequest): Response<Notes> {
        val notes = notesService.updateNotes(request)
        return if (notes != null) {
            Response<Notes>().ok(notes)
        } else {
            Response<Notes>().notFound()
        }
    }

    @GetMapping("/all")
    fun getAllNotes(): Response<List<Notes>> {
        val listNotes = notesService.getAllNotes()
        return Response<List<Notes>>().ok(listNotes)
    }

    @PostMapping("/delete")
    fun deleteNotes(@RequestParam("notes_id") id: Long): Response<String> {
        notesService.deleteNotes(id)
        return Response<String>().ok("Delete success")
    }

}