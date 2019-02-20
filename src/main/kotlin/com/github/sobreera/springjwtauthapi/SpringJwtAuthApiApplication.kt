package com.github.sobreera.springjwtauthapi

import com.github.sobreera.springjwtauthapi.config.WebSecurityConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(WebSecurityConfig::class)
class SpringJwtAuthApiApplication

fun main(args: Array<String>) {
	runApplication<SpringJwtAuthApiApplication>(*args)
}
