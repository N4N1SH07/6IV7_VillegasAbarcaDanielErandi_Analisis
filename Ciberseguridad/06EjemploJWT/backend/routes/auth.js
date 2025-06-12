const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs'); // Corregido: era "bvrypt"
const jwt = require('jsonwebtoken');
const db = require('./bd');

// Ruta para el registro
router.post('/register', async (req, res) => {
    const { email, password } = req.body;
    try {
        // Se hashea la contraseña antes de guardarla
        const hashed = await bcrypt.hash(password, 10);
        db.query('INSERT INTO usuarios (email, password) VALUES (?, ?)', [email, hashed], (err, result) => {
            if (err) {
                console.error('Error al registrar al usuario:', err);
                return res.status(500).send('Error al registrar');
            }
            console.log('Usuario registrado con el ID', result.insertId);
            res.status(200).send('Usuario registrado');
        });
    } catch (error) {
        console.error('Error en el servidor al registrar (register):', error);
        res.status(500).send('Error interno del servidor');
    }
});

// Ruta para el login
router.post('/login', async (req, res) => {
    const { email, password } = req.body; // Corregido: faltaba destructurar email y password
    db.query('SELECT * FROM usuarios WHERE email = ?', [email], async (err, result) => {
        if (err) {
            console.error('Error en la consulta del login:', err);
            return res.status(500).send('Error en el servidor');
        }
        if (result.length === 0) {
            console.warn('Usuario no encontrado:', email);
            return res.status(401).send('Credenciales inválidas');
        }

        const user = result[0];
        const valid = await bcrypt.compare(password, user.password);
        if (!valid) {
            console.warn('Contraseña incorrecta para usuario:', email);
            return res.status(401).send('Contraseña incorrecta, usuario no autorizado');
        }

        const token = jwt.sign(
            { id: user.id, email: user.email },
            process.env.JWT_SECRET,
            { expiresIn: '1h' }
        );

        console.log('Token generado para el usuario:', user.email);
        res.json({ token });
    });
});

module.exports = router;
