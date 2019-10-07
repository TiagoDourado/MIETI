
const express = require('express');
const router = express.Router();
const mongoose=require('mongoose');

const print=require('../models/espaco');

router.get('/', (req, res, next) => {
    res.status(200);
});

module.exports = router;