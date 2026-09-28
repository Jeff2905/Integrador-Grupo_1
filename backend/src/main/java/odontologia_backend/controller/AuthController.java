package odontologia_backend.controller;

import odontologia_backend.entity.Usuario;
import odontologia_backend.repository.UsuarioRepository;
import odontologia_backend.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ============================
    // REGISTRO DE USUARIO
    // ============================
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Usuario usuario) {
        Map<String, Object> respuesta = new HashMap<>();

        // Verificar si el correo ya existe
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            respuesta.put("mensaje", "El correo ya está registrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }

        // Encriptar la contraseña con BCrypt
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Guardar el usuario
        usuarioRepository.save(usuario);

        respuesta.put("mensaje", "Usuario registrado correctamente");
        return ResponseEntity.ok(respuesta);
    }

    // ============================
    // LOGIN DE USUARIO
    // ============================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        Map<String, Object> respuesta = new HashMap<>();

        String email = credenciales.get("email");
        String password = credenciales.get("password");

        // Buscar usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(email).orElse(null);

        // Validar credenciales
        if (usuario == null || !passwordEncoder.matches(password, usuario.getContrasena())) {
            respuesta.put("mensaje", "Correo o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }

        // Generar token JWT
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getIdRol());

        // Respuesta exitosa
        respuesta.put("mensaje", "Inicio de sesión correcto");
        respuesta.put("token", token);
        respuesta.put("idUsuario", usuario.getIdUsuario());
        respuesta.put("nombreUsuario", usuario.getNombreUsuario());
        respuesta.put("idRol", usuario.getIdRol());

        return ResponseEntity.ok(respuesta);
    }
}