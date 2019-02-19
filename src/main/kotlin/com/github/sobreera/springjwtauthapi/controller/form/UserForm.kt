package com.github.sobreera.springjwtauthapi.controller.form

import org.springframework.security.crypto.password.PasswordEncoder

data class UserForm(
    val name: String,
    val pass: String
) {
    fun encrypt(encoder: PasswordEncoder): String {
        return encoder.encode(pass)
    }
}