package com.github.sobreera.springjwtauthapi.repository

import com.github.sobreera.springjwtauthapi.model.Permission

interface PermissionRepository {
    fun findById(id: Long): Permission
    fun findOneByName(name: String): Permission
}