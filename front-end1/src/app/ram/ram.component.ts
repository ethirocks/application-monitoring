
import { CpuDataModel } from '../health-data/cpu.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-ram',
  templateUrl: './ram.component.html',
  styleUrls: ['./ram.component.scss'],
  
})
export class RamComponent implements OnInit {
  
  gg : any = [];
  ud : any = [];
  cc : any = [];
  ee : any = [];
  dd : any = [];
  pp : any = [];
  barChartLabels:number[];
  barChartData:Array<any>;
  barChartData1:Array<any>;


  constructor(private atService : AdventureTimeService) { }

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
    
    this.atService.getDetails("memory").subscribe(function (data1) { 
      this.data = data1
      data1.results.map(function(results) {
          
          results.series.map(function(e) {
            e.values.map(function(eachValueArray) {
              eachValueArray.map(function(value, index) {
                 if (index === 0 ) {
                    a.push(parseMillisecondsIntoReadableTime(value))
                 }else if(index === 1 ){
                  b.push(value)
                 }else if(index === 2 ) {
                   c.push(value)
                 }
                })
                d.push(eachValueArray[2] - eachValueArray[1])
            })
            
          })

      })

    });

      this.barChartData = [b,d]
     this.barChartLabels = a;

     function parseMillisecondsIntoReadableTime(milliseconds){
    
      var hours = milliseconds / (1000*60*60);
      var absoluteHours = Math.floor(hours);
      var h = absoluteHours > 9 ? absoluteHours : '0' + absoluteHours;
    
      var minutes = (hours - absoluteHours) * 60;
      var absoluteMinutes = Math.floor(minutes);
      var m = absoluteMinutes > 9 ? absoluteMinutes : '0' +  absoluteMinutes;
    
      var seconds = (minutes - absoluteMinutes) * 60;
      var absoluteSeconds = Math.floor(seconds);
      var s = absoluteSeconds > 9 ? absoluteSeconds : '0' + absoluteSeconds;
    
    
      return m + ':' + s;
    }

   

  }

  public barChartColors:Array<any> = [
    { 
      backgroundColor: 'red',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { 
      backgroundColor: 'green',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { 
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];

 
  public barChartType:string = 'bar';


  public chartClicked(e:any):void {
    console.log(e);
  }
 
  public chartHovered(e:any):void {
    console.log(e);
  }


  
  
}

  



 