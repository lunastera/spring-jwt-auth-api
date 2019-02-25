package com.github.sobreera.springjwtauthapi.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.sobreera.springjwtauthapi.controller.form.UserForm
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.AUTH_HEADER
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.EXPIRATION_TIME
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.LOGIN_ID
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.LOGIN_URL
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.PASSWORD
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.SECRET
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.TOKEN_PREFIX
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import java.lang.RuntimeException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    private val authenticationM: AuthenticationManager
): UsernamePasswordAuthenticationFilter() {

    private val objectMapper: ObjectMapper = ObjectMapper()

    init {
        // ログイン用のpath変更
        setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher(LOGIN_URL, "POST"))

        // リクエスト時のID/PWのパラメータ名変更
        usernameParameter = LOGIN_ID
        passwordParameter = PASSWORD
    }

    // 認証フロー
    override fun attemptAuthentication(req: HttpServletRequest?, res: HttpServletResponse?): Authentication {
        try {
            // requestパラメータからユーザ情報読み取り
            val user = objectMapper.readValue(req?.inputStream, UserForm::class.java)

            return authenticationM.authenticate(
                UsernamePasswordAuthenticationToken(
                    user.userId,
                    user.password,
                    ArrayList()
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    // 認証に成功
    override fun successfulAuthentication(req: HttpServletRequest?, res: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        val token: String = Jwts.builder()
                .setSubject((authResult?.principal as User).username)   // subは個人を特定できる情報がよい
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.toByteArray())
                .compact()
        res?.addHeader(AUTH_HEADER, TOKEN_PREFIX + token)
    }
}