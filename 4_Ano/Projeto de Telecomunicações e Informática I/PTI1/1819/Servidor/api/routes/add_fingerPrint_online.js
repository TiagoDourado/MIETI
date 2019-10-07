const express = require('express');
const router = express.Router();
const mongoose=require('mongoose');

const FingerPrint=require('../models/FingerPrint');
const FP_online=require("../models/FingerPrint");

function comparator(a, b) {
    return a.euclid - b.euclid;
}


router.post('/', (req, res, next) => {

    const fingerPrint=new FingerPrint({
        _id:new mongoose.Types.ObjectId(),
        MAC: req.body.MAC,
        RSSI:req.body.RSSI,
        point_x:req.body.point_x,
        point_y:req.body.point_y,
        estado: req.body.estado

    });

    console.log(fingerPrint);
    fingerPrint.save().then(result => {   // metodo utilizado no mongoose para guardar o post 
        console.log(result);
        res.status(201).json({
            message:"Handling Post request FingerPrint",
            createUser: result
        });
    })
    .catch(err => {
        console.log(err);
        res.status(500).json({
            error:err
        }); 
    });
});

module.exports = router;

