package com.github.sobreera.springjwtauthapi.repository

import com.github.sobreera.springjwtauthapi.model.User

interface UserRepository {
    fun findById(id: Long): User
    fun findOneByName(name: String): User
}