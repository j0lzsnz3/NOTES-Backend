package com.uhacorp.notes.service.user

import com.uhacorp.notes.exception.AppException
import com.uhacorp.notes.mapper.encryptPassword
import com.uhacorp.notes.mapper.hidePassword
import com.uhacorp.notes.mapper.toUser
import com.uhacorp.notes.model.User
import com.uhacorp.notes.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.jvm.Throws

interface UserService {
    fun register(request: UserRequest): User
    fun login(request: LoginRequest): User?
    fun updateProfile(request: UserRequest): User?
    fun updatePassword(oldPassword: String, email: String, password: String): User?
    fun getProfile(email: String): User?
    fun uploadProfilePicture(userId: Long, imageFile: MultipartFile): Boolean
    fun isUserExistByEmail(email: String): Boolean
    fun isPasswordMatch(password: String, email: String): Boolean
}

@Component
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var appException: AppException

    private val bCryptPasswordEncoder by lazy { BCryptPasswordEncoder() }

    override fun register(request: UserRequest): User {
        val user = request.toUser().encryptPassword(bCryptPasswordEncoder)
        val result = userRepository.save(user)
        return result.hidePassword()
    }

    @Transactional
    override fun login(request: LoginRequest): User? {
        val existingUser = userRepository.findByEmail(request.email)
        return if (existingUser != null) {
            val isPasswordMatch = bCryptPasswordEncoder.matches(request.password, existingUser.password)
            return if (isPasswordMatch) {
                existingUser.hidePassword()
            } else null
        } else null
    }

    override fun updateProfile(request: UserRequest): User? {
        userRepository.updateProfile(request.name, request.birthDay, request.email, request.id ?: 0L)
        return userRepository.findByEmail(request.email)?.hidePassword()
    }

    override fun updatePassword(oldPassword: String, email: String, password: String): User? {
        userRepository.updatePassword(password, email)
        return userRepository.findByEmail(email)?.hidePassword()
    }

    override fun getProfile(email: String): User? {
        return userRepository.findByEmail(email)?.hidePassword()
    }

    @Throws(Exception::class)
    override fun uploadProfilePicture(
        userId: Long,
        imageFile: MultipartFile
    ): Boolean {
        return try {
            val folder = "/Users/moka/Documents/photos/"
            val bytes = imageFile.bytes
            val path = Paths.get(folder.plus(imageFile.originalFilename))
            Files.write(path, bytes)
            userRepository.updateProfilePicture(path.toAbsolutePath().toString(), userId)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    override fun isUserExistByEmail(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }

    override fun isPasswordMatch(password: String, email: String): Boolean {
        val existUser = userRepository.findByEmail(email)
        return if (existUser != null) {
            bCryptPasswordEncoder.matches(password, existUser.password)
        } else {
            false
        }
    }
}