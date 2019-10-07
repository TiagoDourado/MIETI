
const mongoose=require('mongoose');

const table_espaco= mongoose.Schema({
	_id_espaco:mongoose.Schema.Types.ObjectId,
	nome_espaco: {
		type: String,
		require:true
	},
	url_imagem: {
		type:String,
		required: true
	},
	nome_Dono: String
});
module.exports=mongoose.model('Espaco',table_espaco);

