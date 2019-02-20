package com.github.sobreera.springjwtauthapi.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
//@RequestMapping("")
class UserController() {

    @GetMapping("/{id}")
    fun publicData(@PathVariable id: String): PublicData {
        return PublicData("pubic")
    }

    @GetMapping("/{id}/private")
    fun privateData(@PathVariable id: String): PrivateData {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication

        // JWTAuthenticationFilter#successfulAuthenticationで設定したパーミッションを取り出してみる
        val permission: String = authentication.principal.toString()
        return PrivateData("hoge", permission)
    }

    data class PublicData(
        val name: String
    )

    data class PrivateData(
        val name: String,
        val permission: String
    )
}