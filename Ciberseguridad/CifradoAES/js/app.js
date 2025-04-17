document.addEventListener('DOMContentLoaded', function() {
    // Elementos de la UI
    const encryptBtn = document.getElementById('encrypt-btn');
    const decryptBtn = document.getElementById('decrypt-btn');
    const encryptSection = document.getElementById('encrypt-section');
    const decryptSection = document.getElementById('decrypt-section');
    
    const plainText = document.getElementById('plain-text');
    const encryptKey = document.getElementById('encrypt-key');
    const encryptNowBtn = document.getElementById('encrypt-now');
    const encryptResult = document.getElementById('encrypt-result');
    const encryptedText = document.getElementById('encrypted-text');
    const downloadEncryptedBtn = document.getElementById('download-encrypted');
    
    const encryptedFile = document.getElementById('encrypted-file');
    const decryptKey = document.getElementById('decrypt-key');
    const decryptNowBtn = document.getElementById('decrypt-now');
    const decryptResult = document.getElementById('decrypt-result');
    const decryptedText = document.getElementById('decrypted-text');
    
    // Cambiar entre modos cifrar/descifrar
    encryptBtn.addEventListener('click', () => {
        encryptBtn.classList.add('active');
        decryptBtn.classList.remove('active');
        encryptSection.classList.add('active');
        decryptSection.classList.remove('active');
        clearResults();
    });
    
    decryptBtn.addEventListener('click', () => {
        decryptBtn.classList.add('active');
        encryptBtn.classList.remove('active');
        decryptSection.classList.add('active');
        encryptSection.classList.remove('active');
        clearResults();
    });
    
    // Función para limpiar resultados
    function clearResults() {
        encryptResult.classList.add('hidden');
        decryptResult.classList.add('hidden');
    }
    
    // Mostrar alerta de error
    function showError(message, element) {
        // Crear alerta si no existe
        let alert = element.previousElementSibling;
        if (!alert || !alert.classList.contains('alert')) {
            alert = document.createElement('div');
            alert.className = 'alert error';
            element.parentNode.insertBefore(alert, element);
        }
        
        alert.textContent = message;
        alert.classList.remove('hidden');
        
        // Resaltar el campo con error
        element.style.borderColor = 'var(--error-color)';
        element.style.boxShadow = '0 0 0 2px rgba(231, 76, 60, 0.2)';
        
        // Quitar el resaltado después de 3 segundos
        setTimeout(() => {
            alert.classList.add('hidden');
            element.style.borderColor = '#ddd';
            element.style.boxShadow = 'none';
        }, 3000);
    }
    
    // Cifrar mensaje
    encryptNowBtn.addEventListener('click', async () => {
        try {
            // Validar entrada
            if (!plainText.value.trim()) {
                showError('Por favor ingresa un mensaje para cifrar', plainText);
                return;
            }
            
            if (!encryptKey.value) {
                showError('Por favor ingresa una clave de cifrado', encryptKey);
                return;
            }
            
            if (![16, 24, 32].includes(encryptKey.value.length)) {
                showError('La clave debe tener 16, 24 o 32 caracteres', encryptKey);
                return;
            }
            
            // Mostrar carga
            encryptNowBtn.textContent = 'Cifrando...';
            encryptNowBtn.disabled = true;
            
            // Cifrar el mensaje
            const cipher = new AESCipher(encryptKey.value);
            const encrypted = await cipher.encrypt(plainText.value);
            
            // Mostrar resultado
            encryptedText.value = encrypted;
            encryptResult.classList.remove('hidden');
            
            // Restaurar botón
            encryptNowBtn.textContent = 'Cifrar Mensaje';
            encryptNowBtn.disabled = false;
        } catch (error) {
            showError(error.message, encryptKey);
            encryptNowBtn.textContent = 'Cifrar Mensaje';
            encryptNowBtn.disabled = false;
        }
    });
    
    // Descargar mensaje cifrado
    downloadEncryptedBtn.addEventListener('click', () => {
        if (!encryptedText.value) return;
        
        try {
            saveEncryptedToFile(encryptedText.value);
            
            // Mostrar mensaje de éxito
            const alert = document.createElement('div');
            alert.className = 'alert success';
            alert.textContent = 'Archivo descargado correctamente';
            downloadEncryptedBtn.parentNode.insertBefore(alert, downloadEncryptedBtn.nextSibling);
            
            setTimeout(() => {
                alert.classList.add('hidden');
            }, 3000);
        } catch (error) {
            showError('Error al descargar el archivo', downloadEncryptedBtn);
        }
    });
    
    // Descifrar archivo
    decryptNowBtn.addEventListener('click', async () => {
        try {
            // Validar entrada
            if (!encryptedFile.files || !encryptedFile.files[0]) {
                showError('Por favor selecciona un archivo', encryptedFile);
                return;
            }
            
            if (!decryptKey.value) {
                showError('Por favor ingresa una clave de descifrado', decryptKey);
                return;
            }
            
            if (![16, 24, 32].includes(decryptKey.value.length)) {
                showError('La clave debe tener 16, 24 o 32 caracteres', decryptKey);
                return;
            }
            
            // Mostrar carga
            decryptNowBtn.textContent = 'Descifrando...';
            decryptNowBtn.disabled = true;
            
            // Leer el archivo
            const fileContent = await readFileAsText(encryptedFile.files[0]);
            
            // Descifrar el contenido
            const cipher = new AESCipher(decryptKey.value);
            const decrypted = await cipher.decrypt(fileContent);
            
            // Mostrar resultado
            decryptedText.value = decrypted;
            decryptResult.classList.remove('hidden');
            
            // Restaurar botón
            decryptNowBtn.textContent = 'Descifrar Archivo';
            decryptNowBtn.disabled = false;
        } catch (error) {
            showError(error.message, decryptKey);
            decryptNowBtn.textContent = 'Descifrar Archivo';
            decryptNowBtn.disabled = false;
        }
    });
});