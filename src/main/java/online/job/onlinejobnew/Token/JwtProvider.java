package online.job.onlinejobnew.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import online.job.onlinejobnew.Entity.RoleEntiy.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("${spring.security.jwt.token.Secret}")
    private String secretKey;
    @Value("${spring.security.jwt.token.Validity}")
    private long Validity;

    public String generateToken(String username, String email, Role role) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("email", email);
        claims.put("role", role);
        return createToken(username, claims);
    }

    private String createToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Validity))
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] keys = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keys);
    }

    private Claims AllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new SecurityException("Invalid token");
        }
    }

    public String getUsername(String token) {
        return AllClaims(token).getSubject();
    }
    private Date expiration(String token) {
        return AllClaims(token).getExpiration();
    }
    private boolean isExpired(String token) {
        return expiration(token).before(new Date(System.currentTimeMillis()));
    }
    public boolean validateToken(String token , UserDetails userDetails) {
       final String username = getUsername(token);
       return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }
}
