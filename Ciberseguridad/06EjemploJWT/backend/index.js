require('dotenv').config();

const express = require('express');
const cors = require('cors');

const authRouters = require('./routers/auth');

const app = express();
app.use(cors());
app.use(express.json());

app.use('/api/auth', authRouters);

app.listen(3000, () => {
    console.log('servidor en local');
    // Para verificar que dotenv funciona:
    console.log("Usuario BD desde .env:", process.env.BD_USER);
});
