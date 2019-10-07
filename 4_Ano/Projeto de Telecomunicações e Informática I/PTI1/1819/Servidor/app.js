
////////////////////////////////////

const express = require("express");
const app = express();
const morgan = require("morgan");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");
mongoose.Promise=global.Promise;

const users_routes = require("./api/routes/utilizadores");
const orderRoutes = require("./api/routes/orders");
//const fp_online=require("./api/routes/add_fingerPrint_online");

//const fingerPrint_route=require("./api/routes/fingerprints");
const add_espaco=require("./api/routes/espacos");
//const rp=require("./api/routes/add_reference_point");
//const ap=require("./api/routes/add_access_point");
//const algorithm=require("./api/routes/algoritmo");
const fingerprint=require("./api/routes/add_fingerPrint");

/*mongoose.connect("mongodb://PedroDourado:"+process.env.MONGO_ATLAS_PW+"@cluster0-shard-00-00-2i6od.mongodb.net:27017,cluster0-shard-00-01-2i6od.mongodb.net:27017,cluster0-shard-00-02-2i6od.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retry",{
	useNewUrlParser: true
});*/

mongoose.connect("mongodb+srv://user1:"+'testuser'+"@cluster0-2i6od.mongodb.net/",{
	useNewUrlParser: true,
	dbName:'dbpti'
}).then(
	res => console.log('connected'))
.catch(
	err => console.log('error connecting ' + err)
	);

app.use(morgan("dev"));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use('/fotos',express.static('fotos'));


app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept, Authorization"
  );
  if (req.method === "OPTIONS") {
    res.header("Access-Control-Allow-Methods", "PUT, POST, PATCH, DELETE, GET");
    return res.status(200).json({});
  }
  next();
});

// Routes which should handle requests
app.use("/utilizadores", users_routes);
app.use("/orders", orderRoutes);
//app.use("/fingerprints",fingerPrint_route);
app.use("/espacos",add_espaco);
//app.use("/RP",rp);
//app.use("/AP",ap);
//app.use("/algoritmo",algorithm);
app.use("/fingerprint",fingerprint);
//app.use("/fingerPrint_online",fp_online);


app.use((req, res, next) => {
  const error = new Error("Not found");
  error.status = 404;
  next(error);
});

app.use((error, req, res, next) => {
  res.status(error.status || 500);
  res.json({
    error: {
      message: error.message
    }
  });
});

module.exports = app;	
