package com.example.demo.service;

import com.example.demo.configuration.UserPrincipal;
import com.example.demo.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JWTService {


    //Cria a senha secreta
    @Value("${jwt.secret}")
    private String secret;

    //Cria o tempo necessário para expiração
        @Value("${jwt.expiration-ms}")
        private long expirationMs;

        //Cria uma chave
        private SecretKey getSigningKey() {
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            if (keyBytes.length < 32) {
                throw new IllegalArgumentException("JWT secret must be at least 256 bits");
            }
            return Keys.hmacShaKeyFor(keyBytes); // retorna um SecretKey
        }

        //Cria tokens
        public String generateToken(UserDetails userdatails){
            Map<String, Object> claims = new HashMap<>();

            // Captura a role do usuário (ex: ROLE_PROPRIETARIO)
            if (userdatails.getAuthorities() != null) {
                String role = userdatails.getAuthorities()
                        .stream()
                        .findFirst()
                        .map(a -> a.getAuthority().replace("ROLE_", "")) // remove prefixo
                        .orElse(null);

                claims.put("role", role);
                Long userId = ((UserPrincipal) userdatails).getId();
                claims.put("id", userId);

                System.out.println("🔑 Role adicionada ao token: " + role + "\nId do usuário: " + userId);
            }else{
                System.out.println("⚠️ UserDetails não tem authorities!");
            }


            Date now = new Date();
            Date exp = new Date(now.getTime() + expirationMs);


            String token =  Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userdatails.getUsername())
                    .setIssuedAt(now)
                    .setExpiration(exp)
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
            System.out.println("✅ Token gerado: " + token.substring(0, 50) + "...");
            return token;
        }

        public String extractUsername(String token) {
            return Jwts.parser()                      // substitui parserBuilder()
                    .verifyWith(getSigningKey())      // substitui setSigningKey()
                    .build()
                    .parseSignedClaims(token)         // substitui parseClaimsJws()
                    .getPayload()                     // substitui getBody()
                    .getSubject();
        }

    public Long extractId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("id", Long.class);
    }

        public boolean isTokenValid(String token, UserDetails userDetails){
            try{
                final String username = extractUsername(token);
                return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
            }catch(JwtException | IllegalArgumentException e){
                return false;
            }
        }

        private boolean isTokenExpired(String token) {
            Date exp = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration(); // pega a data de expiração correta

            return exp.before(new Date());
        }

}
