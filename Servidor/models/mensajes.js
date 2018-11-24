const mongoose = require('mongoose');
const Schema = mongoose.Schema;

let MensajeSchema = new Schema({
    Emisor: {type: String, required: true},
    Receptor: {type:String, required: true},
    Contenido: {type: String, required: true},
    Tipo: {type:String},
    codCifrado: {type:Number}
});


// Export the model
module.exports = mongoose.model('Mensaje', MensajeSchema);