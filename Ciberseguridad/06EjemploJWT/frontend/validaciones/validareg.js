document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("registerForm");
  
    form.addEventListener("submit", function (event) {
      event.preventDefault(); // Evita envío hasta que se validen los campos
  
      const name = document.getElementById("name").value.trim();
      const secname = document.getElementById("secname").value.trim();
      const Apellidop = document.getElementById("Apellidop").value.trim();
      const Apellidom = document.getElementById("Apellidom").value.trim();
      const password = document.getElementById("password").value;
      const cpassword = document.getElementById("cpassword").value;

      const soloLetras = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;

  
      // Validar campos vacíos
      if (!name || !Apellidop || !Apellidom || !password || !cpassword) {
        alert("Por favor, completa todos los campos.");
        return;
      }
  
      if (!soloLetras.test(name) || !soloLetras.test(Apellidop) || !soloLetras.test(Apellidom)) {
        alert("Los nombres y apellidos solo deben contener letras.");
        event.preventDefault();
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
  
      // Si todo está correcto, puedes enviar el formulario
      alert("Registro exitoso");
      form.submit();
    });
  });
  