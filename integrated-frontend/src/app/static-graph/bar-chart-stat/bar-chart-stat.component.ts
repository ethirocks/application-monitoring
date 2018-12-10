import { map } from 'rxjs/operators';
//import { threadValues, results } from './../thread-data/thread-data.model';
import { AdventureTimeService } from '../../real-time/front-end1/services/adventure-time.service';
// import { DataModel } from '../';
import { ActivatedRoute } from '@angular/router';
import { Component, ElementRef, Input, OnChanges, ViewChild, ViewEncapsulation, HostListener, SystemJsNgModuleLoader, OnInit } from '@angular/core';
import * as d3 from 'd3';
//import * as d3 from 'd3-selection';
import * as d3Scale from 'd3-scale';
import * as d3Shape from 'd3-shape';
import * as d3Array from 'd3-array';
import * as d3Axis from 'd3-axis';
import { timeParse } from 'd3';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
//import { BarChartComponent} from './bar-chart.component.spec';

//import { DataModel } from 'src/app/data/data.model';

@Component({
  selector: 'app-bar-chart',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './bar-chart-stat.component.html',
  styleUrls: ['./bar-chart-stat.component.css'],
  
  

 //template: `<button *ngFor="let button of [1,2,3,4]; let i = index" [ngClass]="{'active':isClicked[i]}" (click)="isClicked[i] = (isClicked[i]? false :true )">up/down</button>`
})
export class BarChartStatComponent implements OnInit {
  // @ViewChild('chart')
  // private chartContainer: ElementRef;

  // @Input()
  // bar: DataModel;

  // margin = {top: 20, right: 30, bottom: 30, left: 50};
  // width = 150 - this.margin.left - this.margin.right;
  // height = 600 - this.margin.top - this.margin.bottom;

  

  
  constructor(private atService : AdventureTimeService,private route: ActivatedRoute) { 
    this.route.params.subscribe( params => {
      this.date = params['date'];
      this.date1 = params['date1'];
      this.from =params['from'];
      this.to = params['to'];
    } );      
  }

 
  errorMsg : string;
  res : any;
  from : number;
  to : number;
  date : any
  date1:any
  svg : any;
  x : any=[];
  y : any=[];
  z : any=[];
  data : any;
  // line: d3Shape.Line<[number, number]>;
  barChartData:Array<any>;
  barChartLabels:Array<any>;

  // ngOnInit() {
    
    
  //   console.log("whaaaaaatttt");
  //   this.atService.getDetails("disk_utilization").subscribe((data:any) =>{ this.bar = data
  //     console.log("error msg " + this.errorMsg);
  //     console.log("bars  "+this.bar);
  //     console.log(this.bar);
  //     this.res = this.bar.results;
  //     // this.createChart();
  //   });
  
    
  // }


  ngOnInit() {
    
   // console.log("ud"+this.gg);
    let a = this.y;
    let b = this.x;
    let c = this.z;
    
    this.atService.getStatic(this.date,this.date1,this.from,this.to,"disk_utilization").subscribe(function (data1) { 
      console.log('come na bro',this.from);
      console.log("ud"+ a);
      this.data = data1
      //  console.log(JSON.stringify(dat))
     // console.log(data1.results.length)
      data1.results.map(function(results) {
          results.series.map(function(e) {
            console.log(e.values)
            e.values.map(function(eachValueArray) {
              console.log(eachValueArray)
              eachValueArray.map(function(value, index) {
                 console.log(value, index ,"index")
                 if (index === 0) {
                    a.push(parseMillisecondsIntoReadableTime(value))
                    // this.ud.push(value)
                 }else if(index === 2){
                   b.push(value)
                    // this.gg.push(value)
                 }else if(index === 3){
                   c.push(value)
                 }
              })
            })
            
          })
          console.log(results.series)
      })
      

    });
  
    this.barChartData = [
      {data: b, label: 'DiskFree'},
      {data: c, label: 'DiskTotal'}
      ];
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

  public barChartColors: Array<any> = [
    {
      backgroundColor : 'black',
      borderColor : 'green',
      pointBackgroundColor : 'rgba(148,159,177,1)',
      pointBorderColor : '#fff',
      pointHoverBackgroundColor : 'green'
    },
    {
      backgroundColor : 'blue',
      borderColor : 'green',
      pointBackgroundColor : 'rgba(148,159,177,1)',
      pointBorderColor : '#fff',
      pointHoverBackgroundColor : 'green'
    }

  ]


public barChartOptions:any = {
  scaleShowVerticalLines: false,
  responsive: true
};


public barChartType:string = 'bar';
public barChartLegend:boolean = true;

}



 

  