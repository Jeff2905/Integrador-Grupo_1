// DentalFlow — main.js
// Punto de entrada del Front-End. Cargado con `defer` para no bloquear el render (WPO).

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("loginForm");
  const togglePassword = document.getElementById("togglePassword");
  const passwordInput = document.getElementById("password");

  // Mostrar / ocultar contraseña
  if (togglePassword && passwordInput) {
    togglePassword.addEventListener("click", () => {
      const isHidden = passwordInput.type === "password";
      passwordInput.type = isHidden ? "text" : "password";
      togglePassword.textContent = isHidden ? "Ocultar" : "Ver";
      togglePassword.setAttribute("aria-label", isHidden ? "Ocultar contraseña" : "Mostrar contraseña");
    });
  }

  // Validación básica de envío (placeholder — reemplazar por la llamada real a la API de autenticación, RF-02)
  if (form) {
    form.addEventListener("submit", (event) => {
      event.preventDefault();

      const email = form.email.value.trim();
      const password = form.password.value;

      if (!email || !password) {
        console.warn("Completa correo y contraseña.");
        return;
      }

      // TODO: reemplazar por la llamada real al endpoint de autenticación (RF-02)
      console.log("Intento de inicio de sesión:", { email });
    });
  }
});
