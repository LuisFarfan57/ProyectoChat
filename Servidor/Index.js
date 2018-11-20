// app.js
const express = require('express');
const bodyParser = require('body-parser');
const mensaje = require('./routes/mensajesRoute'); // Imports routes for the products
// initialize our express app
const usuarioR = require('./routes/usuariosRoute');
const Index = express();
// Set up mongoose connection
const mongoose = require('mongoose');
var cors = require('cors')
let dev_db_url = 'mongodb://admin:admin123@ds229835.mlab.com:29835/proyectochat';
let mongoDB = process.env.MONGODB_URI || dev_db_url;
mongoose.connect(mongoDB);
mongoose.Promise = global.Promise;
let db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));
let port = 1234;
Index.use(cors())
Index.use(bodyParser.json());
Index.use(bodyParser.urlencoded({extended: false}));
Index.use('/mensajes', mensaje);
Index.use('/usuarios', usuarioR);
Index.listen(port, () => {
    console.log('Server is up and running on port numner ' + port);
});