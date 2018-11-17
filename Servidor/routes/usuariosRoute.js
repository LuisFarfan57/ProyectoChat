'use strict'

const express = require('express')
const userCtrl = require('../controllers/usuarioController')
const auth = require('../middlewares/auth')
const api = express.Router()

api.post('/signup', userCtrl.signUp)
api.post('/signin', userCtrl.signIn)
api.get('/private', auth, (req, res) => {
  res.status(200).send({ message: 'Tienes acceso' })
})

module.exports = api