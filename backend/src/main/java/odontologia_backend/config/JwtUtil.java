package odontologia_backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta (debe tener al menos 32 caracteres para HS256)
    private static final String SECRET_KEY = "OdontologiaODAM2026ClaveSecretaSuperSegura123456";

    // Tiempo de expiración: 24 horas en milisegundos
    private static final long EXPIRATION_TIME = 86400000;

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generar token con correo y rol
    public String generateToken(String correo, Integer idRol) {
        return Jwts.builder()
                .subject(correo)
                .claim("idRol", idRol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Extraer correo del token
    public String extractCorreo(String token) {
        return getClaims(token).getSubject();
    }

    // Extraer rol del token
    public Integer extractIdRol(String token) {
        return getClaims(token).get("idRol", Integer.class);
    }

    // Validar si el token es válido
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Obtener los claims del token
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}