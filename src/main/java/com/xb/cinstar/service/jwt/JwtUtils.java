package com.xb.cinstar.service.jwt;

import com.xb.cinstar.service.UserDetailsImpl;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {
    public  static  final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${cinstar.app.jwtSecret}")
    private String jwtSecret;

    @Value("${cinstar.app.jwtExpirationMs}")
    private String jwtExpirationMs;

    @PostConstruct
    private  SecretKey key()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    private Date getDate()
    {
        long currentDate = System.currentTimeMillis();
        long expiration = currentDate + Long.parseLong(jwtExpirationMs);

        // Trả về đối tượng Date từ mili giây
        return new Date(expiration);
    }
    public  String generateTokenFromUsername(String username)
    {
        return Jwts.builder()
                .subject(username)
                .expiration(getDate())
                .issuedAt(new Date())
                .id(UUID.randomUUID().toString()).signWith(key()).compact();
    }
    public String getUserNameFromJwtToken (String authToken)
    {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(authToken).getPayload().getSubject();
    }


    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        return generateTokenFromUsername(userPrincipal.getUsername());
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
