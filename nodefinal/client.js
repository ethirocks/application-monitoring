const http = require('http');
var express = require('express');
const productroutes = require('./monitoringagent');

const HOSTNAME = 'localhost';
const PORT = 8501;

const app = express();

app.use('/products', productroutes)

const server = http.createServer(app);

server.listen(PORT, HOSTNAME, () => {
  console.log(`Server running at http://${HOSTNAME}:${PORT}/`);
});