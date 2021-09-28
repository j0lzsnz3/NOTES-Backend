package com.uhacorp.notes.controller

import com.uhacorp.notes.service.GeneralResponse
import com.uhacorp.notes.service.ResponseError
import com.uhacorp.notes.service.user.LoginRequest
import com.uhacorp.notes.service.user.UserRequest
import com.uhacorp.notes.service.user.UserService
import com.uhacorp.notes.util.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/register")
    fun register(@RequestBody request: UserRequest): ResponseEntity<Any> {
        val isUserExistByName = userService.isUserExistByEmail(request.email)
        return if (isUserExistByName) {
            val errorResponse = ResponseError(
                timestamp = DateUtils.today(),
                status = HttpStatus.CONFLICT.value().toString(),
                error = "User with this email already registered",
                path = "user/register"
            )
            ResponseEntity(errorResponse, HttpStatus.CONFLICT)
        } else {
            val result = userService.register(request)
            ResponseEntity(result, HttpStatus.OK)
        }

    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        val result = userService.login(request)
        return if (result != null) {
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity(
                ResponseError(
                    timestamp = DateUtils.today(),
                    status = HttpStatus.FORBIDDEN.value().toString(),
                    error = "Failed to login, check Email and Password",
                    path = "user/login"
                ),
                HttpStatus.FORBIDDEN
            )
        }
    }

    @GetMapping("/profile")
    fun getProfile(@RequestParam("email") email: String): ResponseEntity<Any> {
        val result = userService.getProfile(email)
        return if (result != null) {
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity(
                ResponseError(
                    timestamp = DateUtils.today(),
                    status = HttpStatus.NOT_FOUND.value().toString(),
                    error = "User with this email not found",
                    path = "user/profile"
                ),
                HttpStatus.NOT_FOUND
            )
        }
    }

    @PostMapping("/profile/password")
    fun updatePassword(
        @RequestParam("old_password") oldPassword: String,
        @RequestParam("password") password: String,
        @RequestParam("email") email: String
    ): ResponseEntity<Any> {
        return if (userService.isPasswordMatch(oldPassword, email)) {
            val result = userService.updatePassword(oldPassword, email, password)
            return if (result != null) {
                ResponseEntity(result, HttpStatus.OK)
            } else {
                ResponseEntity(
                    ResponseError(
                        timestamp = DateUtils.today(),
                        status = HttpStatus.NOT_FOUND.value().toString(),
                        error = "User with this email not found",
                        path = "/profile/password"
                    ),
                    HttpStatus.NOT_FOUND
                )
            }
        } else {
            ResponseEntity(
                ResponseError(
                    timestamp = DateUtils.today(),
                    status = HttpStatus.FORBIDDEN.value().toString(),
                    error = "Wrong password for this email",
                    path = "/profile/password"
                ),
                HttpStatus.FORBIDDEN
            )
        }
    }

    @PostMapping("/profile/update")
    fun updateProfile(@RequestBody request: UserRequest): ResponseEntity<Any> {
        val isUserExist = userService.isUserExistByEmail(request.email)
        return if (isUserExist) {
            val result = userService.updateProfile(request)
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity(
                ResponseError(
                    timestamp = DateUtils.today(),
                    status = HttpStatus.NOT_FOUND.value().toString(),
                    error = "User with this email not found",
                    path = "/profile/update"
                ),
                HttpStatus.NOT_FOUND
            )
        }
    }

    @PostMapping("/profile/picture")
    fun updateProfilePicture(
        @RequestParam("profile_picture") profilePicture: MultipartFile,
        @RequestParam("user_id") id: Long
    ): ResponseEntity<Any> {
        val updateSuccess = userService.uploadProfilePicture(id, profilePicture)
        return if (updateSuccess) {
            ResponseEntity(GeneralResponse("Process complete"), HttpStatus.OK)
        } else {
            ResponseEntity(
                ResponseError(
                    timestamp = DateUtils.today(),
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
                    error = "Failed to upload profile picture",
                    path = "user/profile/picture"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }

}