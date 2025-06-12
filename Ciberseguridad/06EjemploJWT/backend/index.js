
const express = require ('express');
//express es el server 
const cors = require('cors')
//cors es un modulo que crea acceso a las rutas de lso metodos
//los metodos son las ruta de acceso get, post, etc

const authRouters = require('./routers/auth')

require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

app.use('/api/auth', authRouters);

app.listen(3000, ()=>(
    console.log('servidor en local')
))