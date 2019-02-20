package com.github.sobreera.springjwtauthapi.service

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(): UserDetailsService {
    companion object {
        private val usernameList: List<String> = listOf("test", "admin")
        private const val ENCRYPTED_PASSWORD = "$2a$10$5DF/j5hHnbeHyh85/0Bdzu1HV1KyJKZRt2GhpsfzQ8387A/9duSuq" // "password"を暗号化した値
    }
    override fun loadUserByUsername(username: String?): UserDetails {

        // 本来ならここでDBからユーザを検索
        if (!usernameList.contains(username)) {
            throw UsernameNotFoundException(username)
        }

        return User.withUsername(username)
                .password(ENCRYPTED_PASSWORD)
                .authorities("ROLE_USER")
                .build()
    }
}