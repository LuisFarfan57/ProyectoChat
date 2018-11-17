const express = require('express');
const router = express.Router();

const mensaje_controller = require('../controllers/mensajeController');


// a simple test url to check that all of our files are communicating correctly.
router.get('/test', mensaje_controller.test);
router.post('/enviar', mensaje_controller.mensaje_enviar);
router.get('/allmensajes', mensaje_controller.mensajes_contenido);
router.get('/onemensaje/:id', mensaje_controller.mensajeIndividual_contenido);
router.put('/:id/update', mensaje_controller.mensaje_update);
router.delete('/:id/delete', mensaje_controller.mensaje_delete);
module.exports = router;