package com.github.sobreera.springjwtauthapi.support

import com.github.sobreera.springjwtauthapi.support.SecurityConstants.AUTH_HEADER
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.SECRET
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.TOKEN_PREFIX
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(
    authenticationManager: AuthenticationManager
): BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val header: String? = req.getHeader(AUTH_HEADER)

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res)
            return
        }

        // AuthorizationヘッダのBearer Prefixである場合は取得
        val authentication: UsernamePasswordAuthenticationToken? = getAuthentication(req)

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token: String? = request.getHeader(AUTH_HEADER)
        if (token != null) {
            println(token)
            val user: String? = Jwts.parser()
                    .setSigningKey(SECRET.toByteArray())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .body
                    .subject
            if (user != null) {
                return UsernamePasswordAuthenticationToken(user, null, ArrayList())
            }
            return null
        }
        return null
    }

}