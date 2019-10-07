
const express=require('express');
const router=express.Router();

router.get('/',(req,res,next)=>{
	res.status(200).json({
		message: 'Request get products'
	});
});

router.post('/',(req,res,next)=>{
	res.status(200).json({
		message: 'Request post products'
	});
});

router.get('/:productId',(req,res,next)=>{
	const id=req.params.productId;
	if(id==='special'){
		res.status(200).json({
			message:'You discover',
			id:id
		});
	}
	else{
		ŕes.status(200).json({
			message:'you passed an ID'
		})
	}
});


module.exports=router;