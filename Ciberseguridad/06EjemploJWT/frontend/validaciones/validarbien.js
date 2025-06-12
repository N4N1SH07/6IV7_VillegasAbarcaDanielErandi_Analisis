document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");
  
    form.addEventListener("submit", function (event) {
      event.preventDefault(); // Previene envío si hay errores
  
      const name = document.getElementById("name").value.trim();
      const password = document.getElementById("password").value;
  
      const nameRegex = /^[A-Za-zÁÉÍÓÚáéíóúñÑ]+$/;
  
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
  
      // Si todo está bien, se puede enviar el formulario
      alert("Inicio de sesión exitoso.");
      form.submit();
    });
  });
  