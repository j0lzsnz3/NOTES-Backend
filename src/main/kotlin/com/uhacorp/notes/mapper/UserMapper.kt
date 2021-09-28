package com.uhacorp.notes.mapper

import com.uhacorp.notes.model.User
import com.uhacorp.notes.service.user.UserRequest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun UserRequest.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        birthDay = this.birthDay,
        email = this.email,
        profilePicture = this.profilePicture,
        password = this.password
    )
}

fun User.hidePassword(): User {
    return User(
        id = this.id,
        name = this.name,
        birthDay = this.birthDay,
        email = this.email,
        profilePicture = this.profilePicture,
        password = "aku umpetin passwordnya"
    )
}

fun User.encryptPassword(bCryptPasswordEncoder: BCryptPasswordEncoder): User {
    return User(
        id = this.id,
        name = this.name,
        birthDay = this.birthDay,
        email = this.email,
        profilePicture = this.profilePicture,
        password = bCryptPasswordEncoder.encode(this.password)
    )
}