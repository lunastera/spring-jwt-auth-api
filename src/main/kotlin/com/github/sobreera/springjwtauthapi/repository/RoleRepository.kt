package com.github.sobreera.springjwtauthapi.repository

import com.github.sobreera.springjwtauthapi.model.Role

interface RoleRepository {
    fun findById(id: Long): Role
    fun findOneByName(name: String): Role
}