package odontologia_backend.security;

/**
 * Traduce el id_rol almacenado en la tabla `usuarios` a un rol de Spring Security
 * (formato ROLE_XXX, requerido por hasRole()).
 *
 * NOTA: el proyecto no tiene una tabla `roles` con nombres, así que se asume
 * por convención id_rol = 1 -> ADMINISTRADOR. Si tu tabla usa otro id para el
 * administrador, ajusta la constante ID_ROL_ADMINISTRADOR.
 */
public final class RolUtil {

    public static final int ID_ROL_ADMINISTRADOR = 1;

    private RolUtil() {
    }

    public static String obtenerRol(Integer idRol) {
        if (idRol != null && idRol == ID_ROL_ADMINISTRADOR) {
            return "ROLE_ADMINISTRADOR";
        }
        return "ROLE_PACIENTE";
    }
}
