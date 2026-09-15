# Odam — Front-End

Versión inicial del Front-End del sistema de gestión de clientes para la clínica odontológica, correspondiente a la **Parte 2** del taller (Estructura Front-End & WPO).

## Estructura de carpetas

```
frontend/
├── index.html          # Pantalla de login (HU-01: Registro y autenticación de usuarios)
├── css/
│   └── styles.css      # Estilos globales, mobile-first
├── js/
│   └── main.js          # Lógica de interacción (validación, submit del formulario)
├── assets/
│   └── img/             # Imágenes del proyecto (usar formatos modernos: WebP/AVIF)
└── components/           # Componentes reutilizables a futuro (navbar, sidebar, tablas, modales)
```

## Cómo se conecta a las historias de usuario

Esta primera pantalla corresponde a **HU-01 (Registro y autenticación de usuarios)**. Las siguientes pantallas a construir sobre esta misma base de estilos son:

| Carpeta/archivo sugerido | HU |
|---|---|
| `pages/pacientes.html` | HU-02 — Gestión de pacientes y datos personales |
| `pages/citas.html` | HU-03 — Gestión de citas odontológicas y horarios disponibles |
| `pages/pagos.html` | HU-04 — Gestión de pagos y registro de comprobantes |
| `pages/horarios-odontologos.html` | HU-05 — Gestión de horarios y disponibilidad de odontólogos |

Los tokens de color, tipografía y componentes de formulario definidos en `css/styles.css` deben reutilizarse en esas pantallas para mantener consistencia visual.

## Buenas prácticas de WPO aplicadas

- **Fuentes**: `preconnect` a Google Fonts antes de solicitarlas, y `font-display: swap` para evitar texto invisible mientras cargan.
- **CSS**: un único archivo, sin frameworks pesados innecesarios, para minimizar peticiones y peso.
- **JS**: `main.js` se carga con el atributo `defer` para no bloquear el renderizado del HTML.
- **Imágenes** (a futuro, en `assets/img`): usar formatos modernos (WebP/AVIF), comprimidas, y el atributo `loading="lazy"` en imágenes que no sean visibles al cargar la página (`<img loading="lazy">`).
- **Accesibilidad/rendimiento percibido**: foco de teclado visible (`:focus-visible`) y respeto a `prefers-reduced-motion`.

## Cómo continuar (Paso 2.4 — Control de versiones)

```bash
git add frontend/
git commit -m "Estructura inicial del Front-End: login (HU-01) + sistema de estilos base"
git push origin <tu-rama>
```

## Cómo previsualizar

Abre `index.html` directamente en el navegador, o sirve la carpeta con cualquier servidor estático, por ejemplo:

```bash
npx serve frontend
```
