//import { results } from './../thread-data/thread-data.model';
import { CpuDataModel } from '../health-data/cpu.model';
import { AdventureTimeService } from '../../real-time/front-end1/services/adventure-time.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {  ElementRef, Input, OnChanges, ViewChild, ViewEncapsulation, HostListener, SystemJsNgModuleLoader } from '@angular/core';
import * as d3 from 'd3';
import { DeprecatedI18NPipesModule } from '@angular/common';


@Component({
  selector: 'app-ram',
  templateUrl: './ram-stat.component.html',
  styleUrls: ['./ram-stat.component.css'],
  
})
export class RamStatComponent implements OnInit {
  
  gg : any = [];
  ud : any = [];
  cc : any = [];
  ee : any = [];
  dd : any = [];
  pp : any = [];
  from : any;
  to :any;
  barChartLabels:number[];
  barChartData:Array<any>;
  barChartData1:Array<any>;
  date:any
  date1:any
  // count : 0;


  constructor(private atService : AdventureTimeService,private route: ActivatedRoute) {
    this.route.params.subscribe( params => {
      this.date = params['date'];
      this.date1 = params['date1'];
      this.from =params['from'];
      this.to = params['to'];
    } );   
   }

  container : CpuDataModel;
  res : any;


  ngOnInit() {
    console.log("ud"+this.gg);
    let a = this.ud
    let b = this.gg
    let c = this.cc
    let d = this.dd
    let i = this.ee
    let q = this.pp
    // let count = 0;
    
    this.atService.getStatic(this.date,this.date1,this.from,this.to,"memory").subscribe(function (data1) { 
      console.log("ud"+ a);
      this.data = data1
      //  console.log(JSON.stringify(dat))
      console.log("length" + data1.results.length)
      data1.results.map(function(results) {
          
          results.series.map(function(e) {
            console.log(e.values.length+ " val")
            let count = 0;
            e.values.map(function(eachValueArray) {
              //console.log(eachValueArray)
              eachValueArray.map(function(value, index) {
                 //console.log(value, index)
                //  for( var count =0; count<5;count++){
                 if (index === 0 ) {
                    a.push(parseMillisecondsIntoReadableTime(value))
                    // count++;
                    //  console.log("xaxis"+value)
                 }else if(index === 2 ){
                  b.push(value)
                  //  for(var p = 0 ; p<e.values.length; p++){
                  //   i[p] = b.push(value)
                  //  }
                   
                  //  count++;
                    // this.gg.push(value)
                 }else if(index === 3 ) {
                   c.push(value)
                  //  count++;
                  // for(var p = 0 ; p<e.values.length; p++){
                  //     q[p] = c.push(value)
                  // }
                 }

                 
                //  d = c - b
                //  for(var p = 0 ; p<e.values.length; p++){

                //   d[p] = q[p] - i[p];
                   
                 
                })
                d.push(eachValueArray[2] - eachValueArray[1])
                console.log(d ,"d")
            })
            
          })
          console.log(results.series)
      })

    });

      this.barChartData = [b,d]
    //  //this.pieChartData1 = c

    // this.barChartLabels = [a]
   
    // this.barChartData = [
    //   {data: b, label: 'Series A'},
    //   ];
     this.barChartLabels = a;

     function parseMillisecondsIntoReadableTime(milliseconds){
      //Get hours from milliseconds
      // var hours = milliseconds / (1000*60*60);
      // var absoluteHours = Math.floor(hours);
      // var h = absoluteHours > 9 ? absoluteHours : '0' + absoluteHours;
    
      // //Get remainder from hours and convert to minutes
      // var minutes = (hours - absoluteHours) * 60;
      // var absoluteMinutes = Math.floor(minutes);
      // var m = absoluteMinutes > 9 ? absoluteMinutes : '0' +  absoluteMinutes;
    
      // //Get remainder from minutes and convert to seconds
      // var seconds = (minutes - absoluteMinutes) * 60;
      // var absoluteSeconds = Math.floor(seconds);
      // var s = absoluteSeconds > 9 ? absoluteSeconds : '0' + absoluteSeconds;
    
      var date = new Date(milliseconds);

      console.log(date.toString());
    
      return date.toLocaleTimeString();
    
//      return h + ':' + m;
    }


   

  }

  public barChartColors:Array<any> = [
    { // grey
      backgroundColor: 'red',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'green',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];

  // public pieChartLabels:string[] = ['Download Sales', 'In-Store Sales', 'Mail Sales'];
  // public pieChartData:number[] = [300, 500, 100];
  public barChartType:string = 'bar';


  public chartClicked(e:any):void {
    console.log(e);
  }
 
  public chartHovered(e:any):void {
    console.log(e);
  }


  
  
}


  



 