const express = require('express');
const router = express.Router();
const mongoose=require('mongoose');

const FingerPrint=require('../models/FingerPrint');


function comparator(a, b) {
    return a.euclid - b.euclid;
}


/*router.post('/',(req,res,next)=>{
	console.log(req.body.MAC);	
	console.log(req.body.RSSI);

    const fingerPrint=new FingerPrint({
        _id:new mongoose.Types.ObjectId(),
        //MAC: req.body.MAC,
        RSSI:req.body.RSSI,
        point_x:req.body.point_x,
        point_y:req.body.point_y,
        estado: req.body.estado

    });

    console.log(fingerPrint)
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
});*/
    
router.post('/',(req,res,next)=>{
	console.log(req.body.MAC);	
	console.log(req.body.RSSI);

    const fingerPrint=new FingerPrint({
        _id:new mongoose.Types.ObjectId(),
        MAC: req.body.MAC,
        RSSI:req.body.RSSI,
        point_x:req.body.point_x,
        point_y:req.body.point_y,
        nome_espaco:req.body.nome_espaco,
        estado: req.body.estado
    });

    console.log(fingerPrint)
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

router.get('/:nomeEspaco',(req,res,next)=>{
//router.get('/',(req,res,next)=>{
    var nome=req.params.nomeEspaco;
    console.log(nome);

    var somas=[];
    var soma = 0;
    var x    = 0;
    var k    = 3;
    var r=0;
    var l=0;
     //var i=0;   

    var offline=[];
    var online=[];
    var arr=[];
    var array=[];
    var counter=1;
    var tam=0;

    var rssii=[];
    var peso=[];
    var array_posic=[];
    var rssi_online=[];
    var longEstimada = 0;
    var latEstimada  = 0; 
    var k = 0;
    var lim = 10;
    var s = 0;

    var copia;

    FingerPrint.find({estado: "false",nome_espaco:nome},function(err,cal,next) { //fase offline
    //FingerPrint.find({estado: "false"},function(err,cal,next) { //fase offline
        console.log(cal);

        FingerPrint.find({estado: "true",nome_espaco:nome},function(err,posic,next) { //fase online
        //console.log(posic);

            for(var i = 0; i < cal.length/10; i++) {

                while(k < lim) {
                    rssii[s] = cal[k].RSSI;
                    //console.log(cal[k]);
                    s++;
                    k++;
                }
                copia = rssii.slice();

                //console.log(k);
                
                var tmp = {x: counter , rssi: copia, long: cal[k - 1].point_x,lat: cal[k -1].point_y};
                //console.log("x: " +cal[k - 1].point_x);
                //console.log("y: " +cal[k - 1].point_y);
                offline.push(tmp);
               
                lim = lim + 10; 
                s = 0;
                counter++;
            }
            
            //console.log(offline);

            var z = 0;

            var ultimoOn = posic.splice(posic.length - 10, posic.length); // Vai buscar o último ponto online 
            console.log("UltimoOn: " +JSON.stringify(ultimoOn));

            while(z < 10) {
                rssii[s] = ultimoOn[z].RSSI;
                s++;
                z++;
            }
            copia = rssii.slice();
            online = {x: 1 , rssi: copia, long: ultimoOn[z - 1].point_x,lat:ultimoOn[z -1].point_y};

            var j = 0;        
        
            offline.forEach(element => {
                for(var i = 0; i < element.rssi.length; i++) {
                    //console.log(JSON.stringify(element.rssi));
                    //console.log(JSON.stringify(online.rssi));
                    if(element.rssi[i] != 0 || online.rssi[i] != 0){
                        soma += Math.pow((element.rssi[i] - online.rssi[i]), 2);  //online[0].rssi[i] ????   
                    }
                    //console.log(soma);
                }
                //console.log(soma);
                //console.log(Math.sqrt(soma));
                var aux={x: element.x , euclid: Math.sqrt(soma)};
                //console.log(aux);
                somas[j]=(aux);
            
                j++;
                soma = 0;
            });
            
            somas.sort(comparator);  
        
            //console.log(" ");
            console.log("somas: " +JSON.stringify(somas));

            //var somas2=somas.splice(0,3);

            //console.log(somas2);   
            
            var valorK = 3; // Aqui escolhemos qual é que queremos que seja o k
           
            for(var i = 0; i < valorK; i++){ // K  = 3 -> Os 3 vizinhos mais próximos 
                
                peso[i] = 1/somas[i].euclid;
            }

            var somaPeso = 0;

            for(var i = 0; i < valorK; i++) {
                somaPeso += peso[i];
            }
    
            console.log("Soma peso: " +somaPeso);

            var pesoTotal = [];

            for(var i = 0; i < valorK; i++) {
                pesoTotal[i] = peso[i] / somaPeso;
            }

            console.log("Peso total: " +pesoTotal);
            
            for(var i = 0; i < valorK; i++) {
                longEstimada += (offline[somas[i].x - 1].long * pesoTotal[i]); 
                latEstimada  += (offline[somas[i].x - 1].lat * pesoTotal[i]);
            }

            console.log("Long: " +longEstimada);
            console.log("Lat: " +latEstimada);
               
            res.status(200).json({
                longitude:longEstimada,
                latitude:latEstimada
            });
        });    
    })
});


module.exports = router;