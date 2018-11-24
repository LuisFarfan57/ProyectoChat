'use strict'

const services = require('../services')

function isAuth (req, res, next) {
  if (!req.body.Autorizacion) {
    return res.status(403).send({ message: 'No tienes autorización' })
  }

  const token = req.body.Autorizacion.split(' ')[1]

  services.decodeToken(token)
    .then(response => {
      req.user = response
      next()
    })
    .catch(response => {
      res.status(response.status)
    })
}

module.exports = isAuth