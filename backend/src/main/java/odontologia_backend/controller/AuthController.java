package odontologia_backend.controller;

import odontologia_backend.config.JwtService;
import odontologia_backend.entity.Usuario;
import odontologia_backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest datos) {

        Usuario usuario = usuarioRepository
                .findByCorreo(datos.email())
                .orElse(null);

        if (usuario == null) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        if (!usuario.getEstado()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(
                datos.password(),
                usuario.getContrasena())) {

            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        String token = jwtService.generarToken(
                usuario.getIdUsuario(),
                usuario.getCorreo(),
                usuario.getIdRol()
        );

        return new LoginResponse(
                "Inicio de sesión correcto",
                token,
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getIdRol()
        );
    }

    @PostMapping("/registro")
    public String registro(@RequestBody RegistroRequest datos) {

        if (usuarioRepository.findByCorreo(datos.correo()).isPresent()) {
            return "El correo ya está registrado";
        }

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(datos.nombreUsuario());
        usuario.setCorreo(datos.correo());

        usuario.setContrasena(
                passwordEncoder.encode(datos.password())
        );

        usuario.setEstado(true);
        usuario.setIdRol(datos.idRol());

        usuarioRepository.save(usuario);

        return "Usuario registrado correctamente";
    }

    public record LoginRequest(
            String email,
            String password
    ) {}

    public record RegistroRequest(
            String nombreUsuario,
            String correo,
            String password,
            Integer idRol
    ) {}

    public record LoginResponse(
            String mensaje,
            String token,
            Integer idUsuario,
            String nombreUsuario,
            Integer idRol
    ) {}
}