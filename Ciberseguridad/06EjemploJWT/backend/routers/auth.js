const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const db = require('./bd');

router.post('/register', async (req, res) => {
    const { email, password } = req.body;
    try {
        const hashed = await bcrypt.hash(password, 10);
        db.query(
            'INSERT INTO usuarios (email, password) VALUES (?, ?)',
            [email, hashed],
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

router.post('/login',(req, res)=>{
    db.query('SELECT * FROM usuarios WHERE email = ?', [email], async(err, result)=>{
        if(err){
            console.log("Error en la consulta del login", err);
            return res.status(500).send('Error en el servidor');
        }
        if(result.length === 0 ){
            console.log("Usuario no encontrado", err);
                return res.status(500).send('Usuario no encontrado');
        }

        const user = result[0];
//validar pass hasheado
        const valid = await bcrypt.compare(password, user.password);
        if(!valid){
            console.log("Contraseña incorrecta para usuario", email);
            return res.status(401).send('Contraseña incorrecta user no autorizado')
        }
        const token = jwt.sign(
            {id:user.id, email:user.email},
            //proceso del hash
            process.env.jwt_secret,
            {expiresIn:'1h'}
        );

        console.log
        res.json(token)
        

    })
})

module.exports = router;