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
      togglePassword.setAttribute(
        "aria-label",
        isHidden ? "Ocultar contraseña" : "Mostrar contraseña"
      );
    });
  }

  // Inicio de sesión
  if (form) {
    form.addEventListener("submit", (event) => {
      event.preventDefault();

      const email = form.email.value.trim();
      const password = form.password.value;

      if (!email || !password) {
        console.warn("Completa correo y contraseña.");
        return;
      }

      // Conexión con Spring Boot
      fetch("http://localhost:8081/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          email: email,
          password: password
        })
      })
      .then(response => response.text())
      .then(resultado => {

        console.log("Respuesta del servidor:", resultado);

        if (resultado === "Inicio de sesión correcto") {
          alert("Inicio de sesión correcto");
        } else {
          alert("Correo o contraseña incorrectos");
        }

      })
      .catch(error => {
        console.error("Error de conexión:", error);
        alert("No se pudo conectar con el servidor");
      });
    });
  }
});


// CONEXIÓN CON SPRING BOOT - PACIENTES


const API_URL = "http://localhost:8081/api/pacientes";

async function cargarPacientes() {
  try {
    const respuesta = await fetch(API_URL);

    if (!respuesta.ok) {
      throw new Error("No se pudieron obtener los pacientes");
    }

    const pacientes = await respuesta.json();

    console.log("✅ Pacientes obtenidos desde Spring Boot:");
    console.table(pacientes);

  } catch (error) {
    console.error("❌ Error al conectar con Spring Boot:", error);
  }
}

cargarPacientes();