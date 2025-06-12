const mysql = require('mysql2');
require('dotenv').config();
const express = require('express');
const cors = require('cors');

// Crear una conexión con la base de datos
const db = mysql.createConnection({
    host: process.env.BD_HOST,
    user: process.env.BD_USER,
    password: process.env.BD_PASSWORD,
    database: process.env.BD_NAME
});

// Verificar conexión
db.connect(err => {
    if (err) {
        console.error('Error al conectar a la base de datos:', err);
        throw err;
    }
    console.log('Conectado a la base de datos MySQL');
    console.log('Host:', process.env.BD_HOST);
    console.log('Usuario:', process.env.BD_USER);
    console.log('Base de datos:', process.env.BD_NAME);
});

// Exportar la conexión (corregido: 'bd' → 'db')
module.exports = db;
