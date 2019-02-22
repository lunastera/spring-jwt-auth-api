package com.github.sobreera.springjwtauthapi

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

fun main(args: Array<String>) {
    val scanner = ClassPathScanningCandidateComponentProvider(false)
    val controllerPackage = "com.github.sobreera.springjwtauthapi.controller"

    scanner.addIncludeFilter(AnnotationTypeFilter(Controller::class.java))

    val beanSet = scanner.findCandidateComponents(controllerPackage)    // Controllerパッケージをロード

    for (def in beanSet) {
        val clazz = Class.forName(def.beanClassName)
        println("[load-controller] ${clazz.simpleName}")
        clazz.declaredMethods
                .map{ it.getAnnotation(RequestMapping::class.java) }
                .filter { it != null && it.value.isNotEmpty() }
                .forEach { a ->
                    (a.method zip a.value).forEach {
                        println("[endpoints] ${it.first}: ${it.second}")
                    }
                }
        println()
    }
}