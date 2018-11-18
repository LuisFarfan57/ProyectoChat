'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema
const bcrypt = require('bcrypt-nodejs')

const UserSchema = new Schema({
  usuario: { type: String, unique: true, lowercase: true },
  nombre: String,
  apellido: String,
  correo: String,
  password: { type: String }
})

module.exports = mongoose.model('User', UserSchema)