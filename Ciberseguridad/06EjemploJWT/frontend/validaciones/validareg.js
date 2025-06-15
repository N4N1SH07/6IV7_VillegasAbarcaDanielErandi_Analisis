document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("registerForm");

  form.addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita envío hasta que se validen los campos

    const name = document.getElementById("name").value.trim();
    const secname = document.getElementById("secname").value.trim();
    const Apellidop = document.getElementById("Apellidop").value.trim();
    const Apellidom = document.getElementById("Apellidom").value.trim();
    const password = document.getElementById("password").value;
    const cpassword = document.getElementById("cpassword").value;

    const soloLetras = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;

    // Validar campos vacíos
    if (!name || !secname || !Apellidop || !Apellidom || !password || !cpassword) {
      alert("Por favor, completa todos los campos.");
      return;
    }

    // Validar solo letras en campos de texto
    if (
      !soloLetras.test(name) ||
      !soloLetras.test(secname) ||
      !soloLetras.test(Apellidop) ||
      !soloLetras.test(Apellidom)
    ) {
      alert("Los nombres y apellidos solo deben contener letras.");
      return;
    }

    // Validar longitud mínima de contraseña
    if (password.length < 8) {
      alert("La contraseña debe tener al menos 8 caracteres.");
      return;
    }

    // Validar que las contraseñas coincidan
    if (password !== cpassword) {
      alert("Las contraseñas no coinciden.");
      return;
    }
    
    const nameF = name+ ' ' + secname+ ' ' + Apellidop+ ' '+ Apellidom

    // Crear el objeto que se enviará a la API
    const data = {
      nameF,
      password,
    };

    try {
      const response = await fetch("http://localhost:3000/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      let result;
      const contentType = response.headers.get("content-type");

      if (contentType && contentType.includes("application/json")) {
        result = await response.json();
      } else {
        const text = await response.text();
        result = { message: text };
      }

      if (response.ok) {
        alert("Registro exitoso");
        window.location.href = "bienvenida.html";
      } else {
        alert("Error: " + result.message);
      }
    } catch (error) {
      console.error("Error al enviar datos:", error);
      alert("Error de conexión con el servidor");
    }

  });
});
