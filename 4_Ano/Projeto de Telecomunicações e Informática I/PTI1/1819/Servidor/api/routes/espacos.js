const express = require('express');
const router = express.Router();
const multer=require('multer');
const mongoose=require('mongoose');

const Espaco=require('../models/espaco');



const storage=multer.diskStorage({
	destination: function(req,file,cb){
		cb(null,'./fotos');
	},
	filename: function(req,file,cb){
		cb(null,new Date().toISOString() + file.originalname);  //definir o nome do ficheiro com a data de upload
	}
});

const save_limiter=(req,file,cb)=>{     //permite que o ficheiro apenas seja guardado se for do tipo jpeg ou png
	if(file.mimetype==='image/jpeg' || file.mimetype==='image/png' || file.mimetype==='image/jpg'){
		cb(null,true);
	}
	else{
		cb(new Error("can't save"),false);
	}
};

const upload=multer({   //condicoes para que o ficheiro seja guardado
	storage: storage,
	save_file: save_limiter
});



router.get("/", (req, res, next) => {
  Espaco.find()
    .select("nome_espaco nome_Dono url_imagem")
    .exec()
    .then(docs => {
      const response = {
        count: docs.length,
        espacos: docs.map(doc => {
          console.log(doc);
          return {
            nome_espaco: doc.nome_espaco,
            nome_Dono:doc.nome_Dono,
            url_imagem: doc.url_imagem,
            _id: doc._id,
            request: {
              type: "GET",
              url: " http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/espacos/" //+ doc._id
            }
          };
        })
      };
      res.status(200).json(response);
    })
    .catch(err => {
      console.log(err);
      res.status(500).json({
        error: err
      });
    });
});


router.post("/", upload.single('url_imagem'), (req, res, next) => {
  const espaco = new Espaco({
    _id: new mongoose.Types.ObjectId(),
    nome_espaco: req.body.nome_espaco,
    url_imagem: req.file.path,
    nome_Dono:req.body.nome_Dono,
  });
  //console.log(espaco);
  espaco
    .save()
    .then(result => {
      console.log(result);
      res.status(201).json({
        message: "New space available",
        new_espaco: {
            nome_espaco: result.nome_espaco,
            nome_Dono: result.nome_Dono,	
            _id: result._id,
            request: {
                type: 'GET',
                url: "http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/espacos/" //+ result._id
            }
        }
      });
    })
    .catch(err => {
      console.log(err);
      res.status(500).json({
        error: err
      });
    });
});


module.exports = router;