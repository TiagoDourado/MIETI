

const mongoose=require('mongoose');

const table_fingerPrint= mongoose.Schema({
	_id: mongoose.Schema.Types.ObjectId, 
	MAC:{                   //chave estrangeira proveniente do AP
		type:String,     
		require: true
	},
	RSSI:{
		type:Number,
		require:true
	},

	point_x:{
		type:Number,
		
	},
	point_y:{
		type: Number,
	
	},
	nome_espaco: String,
	estado: Boolean
});

module.exports=mongoose.model('FingerPrint',table_fingerPrint);


