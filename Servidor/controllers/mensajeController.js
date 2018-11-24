const Mensaje = require('../models/mensajes');

//Simple version, without validation or sanitation
exports.test = function (req, res) {
    res.send('Greetings from the Test controller!');
};
exports.mensaje_enviar = function (req, res) {
    let message = new Mensaje(
        {
            Emisor: req.body.Emisor,
            Receptor: req.body.Receptor,
            Contenido: req.body.Contenido,
            Tipo: req.body.Tipo,
            codCifrado: req.body.codCifrado
        }
    );

    message.save(function (err) {
        if (err) {
            return next(err);
        }
        res.send('Message Created successfully')
    })
};
exports.mensajes_contenido = function (req, res) {
    Mensaje.find({}, function (err, message) {
        if (err) return next(err);
        res.send(message);
    })
};
exports.mensajeIndividual_contenido = function (req, res) {
    Mensaje.find({Id:req.params.Id}, function (err, message) {
        if (err) return next(err);
        res.send(message);
    })
};
exports.mensaje_update = function (req, res) {
    Mensaje.findByIdAndUpdate(req.params.id, {$set: req.body}, function (err, message) {
        if (err) return next(err);
        res.send('Message udpated.');
    });
};
exports.mensaje_delete = function (req, res) {
    Mensaje.findByIdAndRemove(req.params.id, function (err) {
        if (err) return next(err);
        res.send('Deleted successfully!');
    })
};