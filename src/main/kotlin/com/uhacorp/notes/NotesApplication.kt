package com.uhacorp.notes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@RestController
class NotesBloodApplication

fun main(args: Array<String>) {
	runApplication<NotesBloodApplication>(*args)
}
