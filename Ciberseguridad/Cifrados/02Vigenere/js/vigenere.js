var vigenere = vigenere || (function() {
    var abc = "abcdefghijklmnopqrstuvwxyz";
    var longitud = abc.length;

    var proceso = function(txt, clave, action) {
        var resultado = "";
        var indiceClave = 0;

        for (var i = 0; i < txt.length; i++) {
            var charTexto = txt[i].toLowerCase();
            var indiceTexto = abc.indexOf(charTexto);

            if (indiceTexto !== -1) {
                var charClave = clave[indiceClave % clave.length].toLowerCase();
                var desp = abc.indexOf(charClave);

                var nuevoIndice;
                if (action) {
                    nuevoIndice = (indiceTexto + desp) % longitud;  // Codificar
                } else {
                    nuevoIndice = (indiceTexto - desp + longitud) % longitud;  // Decodificar
                }

                resultado += abc[nuevoIndice];

                indiceClave++;
            } else {
                resultado += charTexto;  // Mantener caracteres no alfabéticos
            }
        }
        return resultado;
    };

    return {
        encode: function(txt, clave) {
            return proceso(txt, clave, true);
        },
        decode: function(txt, clave) {
            return proceso(txt, clave, false);
        }
    };
})();

function codificar() {
    var texto = document.getElementById("txt").value;
    var clave = document.getElementById("txtclave").value;

    if (!texto || !clave) {
        alert("Por favor ingrese tanto el texto como la clave.");
        return;
    }

    document.getElementById("respuesta").value = vigenere.encode(texto, clave);
}

function decodificar() {
    var texto = document.getElementById("txt").value;
    var clave = document.getElementById("txtclave").value;

    if (!texto || !clave) {
        alert("Por favor ingrese tanto el texto como la clave.");
        return;
    }

    document.getElementById("respuesta").value = vigenere.decode(texto, clave);
}

function reiniciar() {
    document.getElementById("txt").value = "";
    document.getElementById("txtclave").value = "";
    document.getElementById("respuesta").value = "";
}

function copiar() {
    var respuesta = document.getElementById("respuesta");
    respuesta.select();
    document.execCommand("copy");
    alert("Texto copiado al portapapeles");
}
