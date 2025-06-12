// Primero definimos el servidor que se encargará del manejo de la app

const express = require('express'); // Express es el framework para el servidor
const cors = require('cors'); // Módulo para permitir solicitudes desde otros orígenes

// Importamos las rutas relacionadas con autenticación (JWT)
const authRouters = require('./routes/auth');

// Cargamos variables de entorno desde el archivo .env
require('dotenv').config();

// Inicializamos la app
const app = express();

// Habilitamos CORS y el uso de JSON en las solicitudes
app.use(cors());
app.use(express.json());

// Usamos las rutas de autenticación bajo el prefijo /api/auth
app.use('/api/auth', authRouters);

// Iniciamos el servidor en el puerto 3000
app.listen(3000, () => {
    console.log('Servidor ejecutándose en http://localhost:3000');
});
