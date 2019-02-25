package com.github.sobreera.springjwtauthapi.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController() {

    @RequestMapping("/{userId}", method = [RequestMethod.GET])
    fun publicData(@PathVariable userId: String): ResponseData {
        return ResponseData(userId, "pubic")
    }

    @RequestMapping("/{userId}/private", method = [RequestMethod.GET])
    fun privateData(@PathVariable userId: String): ResponseData {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication

        // JWTAuthenticationFilter#successfulAuthenticationで設定したユーザ名取ってくる
        val username: String = authentication.principal.toString()
        // この辺でなんか適当にユーザ検索して持ってくればいいと思う
        return ResponseData(username, "private")
    }

    data class ResponseData(
        val name: String,
        val desc: String
    )
}