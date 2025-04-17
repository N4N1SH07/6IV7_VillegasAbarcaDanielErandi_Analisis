function saveEncryptedToFile(encryptedData, filename = 'mensaje_cifrado.aes') {
    try {
        // Crear un blob con los datos
        const blob = new Blob([encryptedData], { type: 'text/plain' });
        
        // Crear un enlace de descarga
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        
        // Simular click para iniciar la descarga
        document.body.appendChild(a);
        a.click();
        
        // Limpiar
        setTimeout(() => {
            document.body.removeChild(a);
            URL.revokeObjectURL(url);
        }, 100);
    } catch (error) {
        console.error('Error al guardar el archivo:', error);
        throw error;
    }
}

async function readFileAsText(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = event => resolve(event.target.result);
        reader.onerror = error => reject(error);
        reader.readAsText(file);
    });
}