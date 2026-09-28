package odontologia_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OdontologiaBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    // Prueba 1: Verifica que el contexto de Spring cargue correctamente
    @Test
    void contextLoads() {
    }

    // Prueba 2: Acceso a ruta protegida SIN token JWT debe ser rechazado
    @Test
    void testAccesoSinTokenDebeSerRechazado() throws Exception {
        mockMvc.perform(get("/api/pacientes"))
               .andExpect(status().isForbidden());
    }

    // Prueba 3: Acceso a ruta administrativa SIN token debe ser rechazado
    @Test
    void testAccesoAdminSinTokenDebeSerRechazado() throws Exception {
        mockMvc.perform(get("/api/admin/panel"))
               .andExpect(status().isForbidden());
    }

    // Prueba 4: Ruta pública de login debe estar accesible (sin token)
    // Nota: Se espera 401 Unauthorized porque las credenciales son falsas,
    // pero la ruta es pública (no requiere token JWT).
    @Test
    void testRutaPublicaLoginEsAccesible() throws Exception {
        mockMvc.perform(post("/api/auth/login")
               .contentType("application/json")
               .content("{\"email\":\"test@test.com\",\"password\":\"123456\"}"))
               .andExpect(status().isUnauthorized());
    }
}