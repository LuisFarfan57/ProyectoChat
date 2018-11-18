'use strict'

const User = require('../models/usuario')
const service = require('../services')

function signUp (req, res) {
  const user = new User({
    usuario: req.body.usuario,
    nombre: req.body.nombre,
    apellido: req.body.apellido,
    correo: req.body.correo,
    password: req.body.password
  })

  user.save((err) => {
    if (err) return res.status(500).send({ message: `Error al crear el usuario: ${err}` })

    return res.status(201).send({ token: service.createToken(user) })
  })
}

function signIn (req, res) {
  User.find({ usuario: req.body.usuario }, (err, user) => {
    if (err) return res.status(500).send({ message: err })
    if (!user) return res.status(404).send({ message: 'No existe el usuario' })

    req.user = user
    res.status(200).send({
      message: 'Te has logueado correctamente',
      token: service.createToken(user)
    })
  })
}
function usuarios_contenido (req, res) {
  User.find({}, function (err, users) {
      if (err) return next(err);
      res.send(users);
  })
};
function usuarioIndividual_contenido (req, res) {
  User.find({Id:req.params.Id}, function (err, usuario) {
      if (err) return next(err);
      res.send(usuario);
  })
};

module.exports = {
  signUp,
  signIn,
  usuarios_contenido,
  usuarioIndividual_contenido
}