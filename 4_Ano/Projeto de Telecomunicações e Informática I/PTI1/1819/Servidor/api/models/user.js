

const mongoose=require('mongoose');

const table_user= mongoose.Schema({
	_id_user:mongoose.Schema.Types.ObjectId,
	name: { //username
		type: String,
		required: true,
		dropDues: true
	},  //username
	
	email: {
		type: String,
		required: true,
		dropDupes: true
	},  
	password: {
		type: String,
		required: true,
	},  
	tipo: {
		type: String,
		default: 'Normal'
	}
});

module.exports=mongoose.model('User',table_user);
