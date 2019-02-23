package com.github.sobreera.springjwtauthapi.model

data class User(
    private val id: Long,
    private val name: String,
    private val password: String,
    private val roles: List<Role>
)