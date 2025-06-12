const jwt = require('jsonwebtoken');

function verificarToken(req, res, next) {
    const token = req.headers['authorization'];
    
    if (!token) {
        console.warn('Token no proporcionado');
        return res.status(403).send('Token requerido');
    }

    // Verificamos la validez del token
    jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
        if (err) {
            console.warn('Token inválido:', err);
            return res.status(401).send('Token inválido');
        }

        // Si es válido, guardamos la información decodificada en la solicitud
        req.user = decoded;
        next();
    });
}

module.exports = verificarToken;
