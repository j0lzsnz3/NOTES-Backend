package com.uhacorp.notes.repository

import com.uhacorp.notes.model.User
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import javax.transaction.Transactional

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.profilePicture =:picture WHERE u.id =:id")
    fun updateProfilePicture(
        @Param(value = "picture") picture: String,
        @Param(value = "id") userId: Long
    )

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name =:name, u.birthDay =:birth_day, u.email =:email WHERE u.id =:id")
    fun updateProfile(
        @Param(value = "name") name: String,
        @Param("birth_day") birthDay: String,
        @Param("email") email: String,
        @Param("id") userId: Long
    )

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password =:password WHERE u.email =:email")
    fun updatePassword(@Param("password") password: String, @Param("email") email: String)
}