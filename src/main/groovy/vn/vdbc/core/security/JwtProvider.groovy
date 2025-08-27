package vn.vdbc.core.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.crypto.SecretKey
import java.util.Date
import java.util.List

@Component
class JwtProvider {

    @Value("\${security.jwt.token.secret-key:secret-key}")
    private String SECRET_KEY

    @Value("\${security.jwt.token.expire-length:3600000}")
    private long EXPIRATION_TIME

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
    }

    String generateToken(String userId, List<String> roles) {
        return Jwts.builder()
                .subject(userId)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact()
    }

    boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
            return true
        } catch (JwtException | IllegalArgumentException e) {
            return false
        }
    }

    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }

    String getUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
        return claims.getSubject()
    }

    String getUserId(HttpServletRequest httpServletRequest) {
        String token = resolveToken(httpServletRequest)
        if (token == null) {
            return null
        }
        return getUserId(token)
    }


}