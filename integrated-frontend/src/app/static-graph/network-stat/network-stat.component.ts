import { AdventureTimeService } from  '../../real-time/front-end1/services/adventure-time.service'; //  '../../services/adventure-time.service';
import { Component, OnInit, ElementRef, Input, OnDestroy } from '@angular/core';
import { CpuDataModel } from '../health-data/cpu.model';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { bar } from '../../real-time/front-end1/health-data/cpu.model';

@Component({
  selector: 'app-network',
  templateUrl: './network-stat.component.html',
  styleUrls: ['./network-stat.component.css']
})
export class NetworkStatComponent implements OnInit {

  private subscription: Subscription = new Subscription();
  

 
  dataSource : object;
  chartConfig: Object;
  res : any;
  svg : any;
  margin = {top: 5, right: 5, bottom: 5, left: 5};
  width : number;
  height : number;
  gg : any = [];
  aa : any = [];
  bb : any = [];
  x : any;
  y : any;
  dd : any =[];
  cc : any=[];
  uu : any =[];
  vv : any =[];
  ww : any=[];
  ee : any =[];
  ff : any=[];
  gh : any=[];
  az : any=[];
  by : any=[];
  cx : any=[];
  dw : any=[];
  xx : any=[];
  yy : any=[];
  valueline : any;
  // linedata : LineData[];
  // str: CpuDataModel;
  ud : any = [];
  // contain: any;
  data: CpuDataModel;
  data4: CpuDataModel;
  str: bar;
  from : any;
  to : any;
  charts :  any = [];
  lineChartData:Array<any>;
  lineChartLabels:Array<any>;
  lineChartData1:Array<any>;
  lineChartLabels1:Array<any>;
  barChartData:Array<any>;
  barChartLabels:Array<any>;
  lineChartData2:Array<any>;
  lineChartLabels2:Array<any>;
  lineChartData5:Array<any>;
  lineChartLabels5:Array<any>;
  lineChartData7:Array<any>;
  lineChartLabels7:Array<any>;
  interval : any;
  ft:any
  tt:any
  date:any
  date1: any
  // lineChartOptions:any = [];



  constructor(private atService : AdventureTimeService, private container : ElementRef,private route: ActivatedRoute) {
    this.route.params.subscribe( params => {
      this.date = params['date'];
      this.date1 = params['date1'];
      this.from =params['from'];
      this.to = params['to'];
    } );   
  }

  @Input()
  contain : CpuDataModel;

  ngOnInit() {

    console.log("display");
    console.log(this.from);
    console.log(this.to);

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


       let u = this.uu

       let v = this.vv
   
       let w = this.ww
   
       let x = this.bb
   
       let y = this.cc
   
       let z = this.dd
   
       this.atService.getStatic(this.date,this.date1,this.from,this.to,"networkMetrics").subscribe(function (data1) { 
   
         //console.log("ud"+ this.ft);
   
         this.data = data1
   
         //  console.log(JSON.stringify(dat))
   
         //console.log(data1.results.length)
   
         data1.results.map(function(results) {
   
   
             results.series.map(function(e) {
   
               //console.log(e.values)
   
               e.values.map(function(eachValueArray) {
   
                  console.log(eachValueArray)
   
                 eachValueArray.map(function(value, index) {
   
                    //console.log(value, index)
   
                    if (index === 0) {
   
                     //console.log(value,"bta");  
                     //console.log("please",parseMillisecondsIntoReadableTime(value));
                     u.push(parseMillisecondsIntoReadableTime(value));
   
                       // this.ud.push(value)
   
                    }else if(index === 3) {
   
                     //console.log(value,"bta bhai");
   
                      v.push(value)
   
                       // this.gg.push(value)
   
                    }
   
                    else if(index === 11){
   
                      w.push(value)
   
                    }
   
                    else if(index === 1){
   
                      x.push(value)
   
                    }
   
                    else if(index === 2){
   
                      y.push(value)
   
                    }
   
                    else if(index === 10){
   
                      z.push(value)
   
                    }
   
                 })
   
               })
   
               
   
             })
   
             //console.log(results.series)
   
         })
   
       });
   
       // console.log("sjfhjhsjh",this.str[0][0][1][1]);
   
   
   
       
       this.lineChartData7 = [
   
         {data: v, label: 'Downlaod Speed'},
   
         {data: w, label: 'Upload Speed'},
   
         {data: x, label: 'Data in rate'},
   
         {data: y, label: 'Data out rate'},
   
         {data: z, label: 'Max_transmission_rate'},
   
         ];
   
         //console.log(a);
   
        this.lineChartLabels7 = u;
  
    }


    public lineChartColors2:Array<any> = [
     
      { // dark grey
        //backgroundColor: 'white',
        borderColor: 'lightgreen',
      },
      { // grey
        //backgroundColor: 'white',
        borderColor: 'skyblue',
        
      },
      { // grey
        //backgroundColor: 'white',
        borderColor: 'yellow',
        //pointBackgroundColor: 'rgba(148,159,177,1)',
        //pointBorderColor: '#fff',
        //pointHoverBackgroundColor: '#fff',
        //pointHoverBorderColor: 'rgba(148,159,177,0.8)'
      },
      { // grey
        //backgroundColor: 'white',
        borderColor: 'red',
        //pointBackgroundColor: 'rgba(148,159,177,1)',
        //pointBorderColor: '#fff',
        //pointHoverBackgroundColor: '#fff',
        //pointHoverBorderColor: 'rgba(148,159,177,0.8)'
      }
    ];





  public lineChartColors7:Array<any> = [

    { // grey

      //backgroundColor: 'rgba(148,159,177,0.2)',

      borderColor: 'red',

      //pointBackgroundColor: 'rgba(148,159,177,1)',

      //pointBorderColor: '#fff',

      //pointHoverBackgroundColor: '#fff',

      //pointHoverBorderColor: 'rgba(148,159,177,0.8)'

    },

    { // dark grey

      //backgroundColor: 'rgba(77,83,96,0.2)',

      borderColor: 'green',

      //pointBackgroundColor: 'rgba(77,83,96,1)',

      //pointBorderColor: '#fff',

      //pointHoverBackgroundColor: '#fff',

      //pointHoverBorderColor: 'rgba(77,83,96,1)'

    },

    { // grey

      //backgroundColor: 'rgba(148,159,177,0.2)',

      borderColor: 'blue',

      //pointBackgroundColor: 'rgba(148,159,177,1)',

      //pointBorderColor: '#fff',

      //pointHoverBackgroundColor: '#fff',

      //pointHoverBorderColor: 'rgba(148,159,177,0.8)'

    },

    { // grey

      //backgroundColor: 'rgba(148,159,177,0.2)',

      borderColor: 'yellow',

      //pointBackgroundColor: 'rgba(148,159,177,1)',

      //pointBorderColor: '#fff',

      //pointHoverBackgroundColor: '#fff',

      //pointHoverBorderColor: 'rgba(148,159,177,0.8)'

    },

    { // grey

      //backgroundColor: 'rgba(148,159,177,0.2)',

      borderColor: 'black',

      //pointBackgroundColor: 'rgba(148,159,177,1)',

      //pointBorderColor: '#fff',

      //pointHoverBackgroundColor: '#fff',

      //pointHoverBorderColor: 'rgba(148,159,177,0.8)'

    }

  ];
    public lineChartColors5:Array<any> = [
      // { // grey
      //   backgroundColor: 'white',
      //   borderColor: 'pink',
      //   pointBackgroundColor: 'rgba(148,159,177,1)',
      //   pointBorderColor: '#fff',
      //   pointHoverBackgroundColor: '#fff',
      //   pointHoverBorderColor: 'rgba(148,159,177,0.8)'
      // },
      { // dark grey
        //backgroundColor: 'white',
        borderColor: 'lightgreen',
        // pointBackgroundColor: 'rgba(77,83,96,1)',
        // pointBorderColor: '#fff',
        // pointHoverBackgroundColor: '#fff',
        // pointHoverBorderColor: 'rgba(77,83,96,1)'
      },
      { // grey
        //backgroundColor: 'white',
        borderColor: 'skyblue',
        //pointBackgroundColor: 'rgba(148,159,177,1)',
        //pointBorderColor: '#fff',
        //pointHoverBackgroundColor: '#fff',
        //pointHoverBorderColor: 'rgba(148,159,177,0.8)'
      },
      { // grey
        //backgroundColor: 'white',
        borderColor: 'yellow',
        //pointBackgroundColor: 'rgba(148,159,177,1)',
        //pointBorderColor: '#fff',
        //pointHoverBackgroundColor: '#fff',
        //pointHoverBorderColor: 'rgba(148,159,177,0.8)'
      },
      { // grey
        //backgroundColor: 'white',
        borderColor: 'red',
        //pointBackgroundColor: 'rgba(148,159,177,1)',
        //pointBorderColor: '#fff',
        //pointHoverBackgroundColor: '#fff',
        //pointHoverBorderColor: 'rgba(148,159,177,0.8)'
      }
    ];

    public lineChartColors1:Array<any> = [
      { // grey
        backgroundColor: 'white',
        borderColor:  'green',//'rgba(148,159,177,1)',
        pointBackgroundColor: 'brown',//'rgba(148,159,177,1)',
        pointBorderColor: 'red',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'coral',//'rgba(148,159,177,0.8)',
        pointStyle: 'rectRounded',
        pointRadius: 8
      }]


  public lineChartColors:Array<any> = [
    { // grey
      backgroundColor: 'white',
      borderColor:  'coral',//'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
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
  public lineChartLegend:boolean = true;
  public lineChartType:string = 'line';
  public lineChartOptions: any = {
    resposive : false,
    
      
        
      
    
  }
  public chartClicked(e:any):void {
    console.log(e);
  }
 
  public chartHovered(e:any):void {
    console.log(e);
  }

  public barChartOptions:any = {
    scaleShowVerticalLines: false,
    responsive: true,
    // animation: false,
    // //Boolean - If we want to override with a hard coded scale
    // scaleOverride: true,
    // //** Required if scaleOverride is true **
    // //Number - The number of steps in a hard coded scale
    // scaleSteps: 10,
    // //Number - The value jump in the hard coded scale
    // scaleStepWidth: 10,
    // //Number - The scale starting value
    // scaleStartValue: 0
  }
  
  
  public barChartType:string = 'bar';
  public barChartLegend:boolean = true;




  // var myLineChart = new Chart(ctx).Line(data, options);

  
  // var months = ["January", "February", "March", "April", "May", "June",
  //   "July", "August", "September", "October", "November", "December"
  // ];


  
}
