package com.github.sobreera.springjwtauthapi.controller

import com.github.sobreera.springjwtauthapi.controller.form.UserForm
import com.github.sobreera.springjwtauthapi.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
//@RequestMapping("")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun publicData(@PathVariable id: String): PublicData {
        return PublicData("pubic")
    }

    @GetMapping("/{id}/private")
    fun privateData(@PathVariable id: String): PrivateData {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        TODO()
    }

    data class PublicData(
        val name: String
    )

    data class PrivateData(
        val name: String,
        val permission: String
    )
}