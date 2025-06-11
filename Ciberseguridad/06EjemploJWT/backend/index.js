// Lo primero es definir el servidor que se encargará de gestionar el backend de la app

const express = require('express');
// Express es el framework que nos permite crear el servidor

const cors = require('cors');
// CORS es un módulo que permite gestionar el acceso a las rutas desde diferentes dominios
// Esto incluye métodos como GET, POST, PUT, DELETE, etc.

// Vamos a utilizar JWT para acceder mediante autenticación
const authRouters = require('./routes/auth');

// Cargamos las variables de entorno desde el archivo .env
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

// Definimos las rutas para autenticación
app.use('/api/auth', authRouters);

// Iniciamos el servidor en el puerto 3000
app.listen(3000, () => {
    console.log('Servidor corriendo en http://localhost:3000');
});
