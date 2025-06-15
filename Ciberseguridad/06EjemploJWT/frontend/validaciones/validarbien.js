document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");
  
    form.addEventListener("submit", async function (event) {
      event.preventDefault(); // Previene envío si hay errores
  
      const name = document.getElementById("name").value.trim();
      const password = document.getElementById("password").value;
  
      const nameRegex = /^[A-Za-zÁÉÍÓÚáéíóúñÑ\s]+$/;
  
      // Verificar campos vacíos
      if (!name || !password) {
        alert("Por favor, completa todos los campos.");
        return;
      }
  
      // Verificar que el nombre solo contenga letras
      if (!nameRegex.test(name)) {
        alert("El nombre solo debe contener letras.");
        return;
      }

      const data = {
        name,
        password,
      };


      try {
        const response = await fetch("http://localhost:3000/api/auth/login", {
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
          alert("Inicio exitoso");
          window.location.href = "perfect.html";
        } else {
          alert("Error: " + result.message);
        }
      } catch (error) {
        console.error("Error al enviar datos:", error);
        alert("Error de conexión con el servidor");
      }



    });
  });
  