package com.github.sobreera.springjwtauthapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringJwtAuthApiApplication

fun main(args: Array<String>) {
	runApplication<SpringJwtAuthApiApplication>(*args)
}
