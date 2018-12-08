const express = require('express');
const router = express.Router();
var appmetrics = require('appmetrics');
var monitoring = appmetrics.monitor();

router.get('/cpu',(req,res,next)=>
{       
       
    var userID= req.query.userID;
    var applicationID = req.query.applicationID;
        var check = true;
        monitoring.on('cpu', function (cpu) {
            
            if(check){
            res.status(200).json({
                userID:userID,
                applicationID:applicationID,
               metrics:{
                cpu:parseFloat(cpu.process*100).toFixed(3),
                systemcpu:parseFloat(cpu.system*100).toFixed(3)}
            })
            check=false;
            }
        });
})

router.get('/memory',(req,res,next)=>
{
    var userID= req.query.userID;
    var applicationID = req.query.applicationID;
    var check = true;
        monitoring.on('memory', function (memory) {
            
            if(check){
            res.status(200).json({
                userID:userID,
                applicationID:applicationID,
                metrics:{
                memory:(parseFloat(memory.physical_used/(1024*1024*1024)).toFixed(3)),
                totalmemory:(parseFloat(memory.physical_total/(1024*1024*1024)).toFixed(3))}
            })
            check=false;
            }
        });
})




router.get('/temperature',(req,res,next)=>
{
    var userID= req.query.userID;
        var applicationID = req.query.applicationID;
    var spawn = require('child_process').spawn;
    const si = require('systeminformation');
    temp = spawn('cat', ['/sys/class/thermal/thermal_zone0/temp']);
        temp.stdout.on('data', function(data) {
                res.status(200).json({
                  userID:userID,
                  applicationID:applicationID,
                  metrics:{
                    temperature:0
                  }
                })
         })
    
            
})

module.exports=router;
