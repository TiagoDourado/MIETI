
const mongoose=require('mongoose');

const table_AP=mongoose.Schema({
	MAC:{
		type:String,
		require: true
	},
	RSSI:{
		type:Number,
		default: '0'
	},
	estado: Boolean
})

module.exports=mongoose.model('AP',table_AP);