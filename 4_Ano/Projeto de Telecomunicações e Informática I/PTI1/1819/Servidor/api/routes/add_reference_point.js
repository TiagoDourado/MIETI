const express = require('express');
const router = express.Router();
const mongoose=require('mongoose');

const RP=require('../models/RP');

router.post('/',(req,res,next)=>{
	console.log(req.body.point_x);
	console.log(req.body.point_y);

    const point=new RP({
        _id:new mongoose.Types.ObjectId(),
        //name_space: req.body.name,
        point_x: req.body.point_x,
        point_y:req.body.point_y,
    });

    console.log(point)
    point.save().then(result => {   // metodo utilizado no mongoose para guardar o post 
        console.log(result);
        res.status(201).json({
            message:"Handling Post request RP",
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


router.get('/', (req, res, next) => {
    RP.find().exec()
    .then(docs => {
        console.log(docs);
       // if(docs.length >=0){
            res.status(200).json(docs);
        /*}
        else{
            res.status(404).json({
                message: 'No entries found'
            });
        }*/

    })
    .catch(err => {
        console.log(err);
        res.status(500).json({
            error:err
        });
    });
});


module.exports = router;