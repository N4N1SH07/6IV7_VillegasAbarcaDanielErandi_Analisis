const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const db = require('../bd');

router.post('/register', async (req, res) => {
    const { nameF, password } = req.body;
    try {
        const hashed = await bcrypt.hash(password, 10);
        db.query(
            'INSERT INTO usuarios (name, password) VALUES (?, ?)',
            [nameF, hashed],
            (err, result) => {
                if (err) {
                    console.log('ERROR AL REGISTRAR AL USUARIO', err);
                    return res.status(500).send('Error al registrar');
                }
                console.log("Usuario registrado con el ID", result.insertId);
                res.status(200).send('Usuario registrado correctamente');
            }
        );
    } catch (error) {
        console.error('Error en el servidor:', error);
        res.status(500).send('Error del servidor');
    }
});

router.post('/login', (req, res) => {
  const { name, password } = req.body;

  console.log("Intentando login con:", name, password); // DEBUG

  db.query('SELECT * FROM usuarios WHERE name = ?', [name], async (err, result) => {
    if (err) {
      console.log("Error en la consulta del login", err);
      return res.status(500).send('Error en el servidor');
    }

    if (result.length === 0) {
      console.log("Usuario no encontrado");
      return res.status(404).json({ message: 'Usuario no encontrado' });
    }

    const user = result[0];
    const valid = await bcrypt.compare(password, user.password);
    if (!valid) {
      console.log("Contraseña incorrecta para usuario", name);
      return res.status(401).json({ message: 'Contraseña incorrecta' });
    }

    const token = jwt.sign(
      { id: user.id, name: user.name },
      process.env.JWT_SECRET, 
      { expiresIn: '1h' }
    );

    console.log("Usuario autenticado:", name);
    console.log("Token:", token);
    res.json({ token });
  });
})

module.exports = router;