const express = require('express');
const router = express.Router();
const mongoose=require('mongoose');

const User=require('../models/user');


router.get('/', (req, res, next) => {
    User.find().exec()
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

router.post('/',(req,res,next)=>{
    const user=new User({
        _id:new mongoose.Types.ObjectId(),
        name: req.body.name,
        password: req.body.password,
        email:req.body.email,
        tipo:req.body.tipo,
    });
    console.log(user)
    user.save().then(result => {   // metodo utilizado no mongoose para guardar o post 
        console.log(result);
        res.status(201).json({
            message:"Handling Post request to /products",
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


   router.post('/', function(req,res,next){
    const user=req.body.name;
    const pass=req.body.password;
    //const type=req.body.tipo;
    console.log(user);
    console.log(pass);
    //console.log(type);
    
    User.find({name: user},function(err,docs){
    console.log(docs);  
    if(docs.length==0){
        res.status(400).json({status:"fail"});
        console.log("erro");
    }
    else{
        if(docs[0].password==pass){

            if(docs[0].tipo=="Normal"){
                res.status(200).json({status:"ok normal"});
            }
            if(docs[0].tipo=="Dono"){
                res.status(200).json({status:"ok dono"});
            }
        }
        else{
            res.status(400).json({status:"fail"});
        }
    }
   });
});

router.post('/login',(req,res,next)=>{
    User.find({name:req.body.name})
    .exec()
    .then(array =>{
        if(array.length < 1){
            return res.status(404).json({
                message: "User doesn't exists"    
            })
        }
        compare(req.body.password,array[0].password,(err,result)=>{
            if(err){
                return res.status(401).json({
                    message: 'Authentication failed'
                });
            }
            if(result){
                message:'Authentication confirmed'
            }
            res.status(401).json({
                message: 'Authentication failed'
            });    

        })
    })
    .catch(err => {
        console.log(err);
        res.status(500).json({
            error:err
        }); 
    });
})

router.get("/:username/:password", (req,res,next) =>{
    const user=req.params.username;
    const pass=req.params.password;
    //const type=req.body.tipo;
    console.log(user);
    console.log(pass);
    //console.log(type);
    
    User.find({name: user},function(err,docs){
    console.log(docs);  
    if(docs.length==0){
        res.status(400).json({status:"fail"});
        console.log("erro");
    }
    else{
        if(docs[0].password==pass){

            if(docs[0].tipo=="Normal"){
                res.status(200).json({status:"ok normal"});
            }
            if(docs[0].tipo=="Dono"){
                res.status(200).json({status:"ok dono"});
            }

            if(docs[0].tipo=="Premium"){
                res.status(200).json({status:"premium"});
            }
        }
        else{
            res.status(400).json({status:"fail"});
        }
    }
   });
});


/*router.post("/login", (req,res,next) =>{
    const user=req.params.username;
    const pass=req.params.password;
    //const type=req.body.tipo;
    console.log(user);
    console.log(pass);
    //console.log(type);
    
    User.find({name: user},function(err,docs){
    console.log(docs);  
    if(docs.length==0){
        res.status(400).json({status:"fail"});
        console.log("erro");
    }
    else{
        if(docs[0].password==pass){

            if(docs[0].tipo=="Normal"){
                res.status(200).json({status:"ok normal"});
            }
            if(docs[0].tipo=="Dono"){
                res.status(200).json({status:"ok dono"});
            }
        }
        else{
            res.status(400).json({status:"fail"});
        }
    }
   });
});*/


router.get('/:username', (req, res, next) => {  //get pelo ID
    const user = req.params.name;
    User.findById(user)
    .exec()
    .then(doc =>{
        console.log("From database",doc);
        if(doc){
            res.status(200).json(doc);
        }
        else{
            res.status(404).json({message:" No valid entrey for found for provided ID"});
        }
    });
});

/*router.patch('/:username', (req, res, next) => {
   const name=req.params.name; 
   const updateOPS={};
   for(const ops of req.body){
        update[ops.propName]=ops.value;
   }
   User.update({name:username},   //para poder mudar algo, é necessario $ no que pretendemos mudar
        {$set:updateOPS}).exec()
        .then( result =>{
            console.log(result);
            res.status(200).json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
        });
    });    
});*/

router.patch('/:username',function(req,res){
    var update_obj=req.body;
    console.log(update_obj);
    var user=req.params.username;
    User.update({name:user},   //para poder mudar algo, é necessario $ no que pretendemos mudar
        {$set:update_obj}).exec()
        .then( result =>{
            console.log(result);
            res.status(200).json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
        });
    }); 
});


router.delete('/:username', (req, res, next) => {
    console.log(res.body);
    const user_delete=req.params.username;    
    User.remove({ username: user_delete}).exec().then(res => {
        res.status(200).json(result);
    })
    .catch(err=>{
        console.log(err);
        res.status(500).json({
            error: err
        });
    });
});

module.exports = router;