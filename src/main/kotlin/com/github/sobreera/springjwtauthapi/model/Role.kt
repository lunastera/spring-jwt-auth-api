package com.github.sobreera.springjwtauthapi.model

data class Role(
    private val id: Long,
    private val name: String,
    private val permissions: List<Permission>
)