'use strict'

const express = require('express')
const userCtrl = require('../controllers/usuarioController')
const auth = require('../middlewares/auth')
const services = require('../services')
const api = express.Router()

api.post('/signup', userCtrl.signUp)
api.post('/signin', userCtrl.signIn)
api.get('/getusuarios', userCtrl.usuarios_contenido)
api.delete('/eliminar/:id', userCtrl.usuario_eliminar)
api.get('/getoneusuario/:id', userCtrl.usuarioIndividual_contenido)
api.post('/private', (req, res,next) => {
  if (!req.body.Autorizacion) {
   // res.status(403).send({ message: 'No tienes autorización' })
  }

  const token = req.body.Autorizacion

  services.decodeToken(token)
    .then(response => {
      req.user = response
      res.send({ token: 'Tienes autorizacion' })
      next()
    })
    .catch(response => {
      
      res.send({ token: 'No Tienes autorizacion' })
    })
    
})


module.exports = api