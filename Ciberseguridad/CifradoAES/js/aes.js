// Implementación de AES (usando la API Web Crypto)
class AESCipher {
    constructor(key) {
        this.key = key;
    }

    async encrypt(plaintext) {
        try {
            // Validar la clave
            if (![16, 24, 32].includes(this.key.length)) {
                throw new Error('La clave debe tener 16, 24 o 32 caracteres');
            }

            // Codificar el texto plano como UTF-8
            const encodedText = new TextEncoder().encode(plaintext);

            // Generar un IV (Initialization Vector) aleatorio
            const iv = crypto.getRandomValues(new Uint8Array(16));

            // Importar la clave
            const cryptoKey = await crypto.subtle.importKey(
                'raw',
                new TextEncoder().encode(this.key),
                { name: 'AES-CBC' },
                false,
                ['encrypt']
            );

            // Cifrar el texto
            const ciphertext = await crypto.subtle.encrypt(
                {
                    name: 'AES-CBC',
                    iv: iv
                },
                cryptoKey,
                encodedText
            );

            // Combinar IV y texto cifrado
            const encryptedData = new Uint8Array(iv.length + ciphertext.byteLength);
            encryptedData.set(iv, 0);
            encryptedData.set(new Uint8Array(ciphertext), iv.length);

            // Convertir a Base64 para almacenamiento
            return btoa(String.fromCharCode.apply(null, encryptedData));
        } catch (error) {
            console.error('Error en encrypt:', error);
            throw error;
        }
    }

    async decrypt(base64Ciphertext) {
        try {
            // Validar la clave
            if (![16, 24, 32].includes(this.key.length)) {
                throw new Error('La clave debe tener 16, 24 o 32 caracteres');
            }

            // Convertir de Base64 a Uint8Array
            const encryptedData = new Uint8Array(atob(base64Ciphertext).split('').map(c => c.charCodeAt(0)));

            // Extraer el IV (primeros 16 bytes)
            const iv = encryptedData.slice(0, 16);

            // Extraer el texto cifrado (resto de bytes)
            const ciphertext = encryptedData.slice(16);

            // Importar la clave
            const cryptoKey = await crypto.subtle.importKey(
                'raw',
                new TextEncoder().encode(this.key),
                { name: 'AES-CBC' },
                false,
                ['decrypt']
            );

            // Descifrar el texto
            const decrypted = await crypto.subtle.decrypt(
                {
                    name: 'AES-CBC',
                    iv: iv
                },
                cryptoKey,
                ciphertext
            );

            // Decodificar a string
            return new TextDecoder().decode(decrypted);
        } catch (error) {
            console.error('Error en decrypt:', error);
            throw new Error('Clave incorrecta o archivo corrupto');
        }
    }
}