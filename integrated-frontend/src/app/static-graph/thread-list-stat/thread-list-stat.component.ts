import { map } from 'rxjs/operators';
// import { threadValues } from './../data/data.model';
// import { threadValues, results } from './../thread-data/thread-data.model';
import { LineData, threadValues ,bar} from './../health-data/cpu.model';
import { AdventureTimeService } from '../../real-time/front-end1/services/adventure-time.service';
import { Component, OnInit, ElementRef, Input, OnDestroy } from '@angular/core';
import { CpuDataModel } from '../health-data/cpu.model';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import * as d3 from 'd3';
import { symbolTriangle } from 'd3';
import { asPureExpressionData } from '@angular/core/src/view';
import { DeprecatedI18NPipesModule } from '@angular/common';

@Component({
  selector: 'app-thread-list',
  templateUrl: './thread-list-stat.component.html',
  styleUrls: ['./thread-list-stat.component.css']
})
export class ThreadListStatComponent implements OnInit{

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
  pp : any=[];
  yy : any=[];
  valueline : any;
  linedata : LineData[];
  // str: CpuDataModel;
  ud : any = [];
  from : any;
  to : any;
  // contain: any;
  data: CpuDataModel;
  data4: CpuDataModel;
  str: bar;
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
  date:any
  date1:any
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

  // ngOnInit() {
  //   let count=0;
  //   this.refreshData();

  //    this.popData();
  //   this.interval = setInterval(() => { 
  //       this.refreshData(); 
       
  //   }, 10000);

  // //   this.interval = setInterval(()=>{
  // //     this.popData();
  // //  }, 2000)
    
  // }

 

// ngOnPop(){
  
// }


  ngOnInit() {
    

    // this.popData();

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


    
   


      let h = this.xx
      let i = this.yy
      let j = this.az
      let k = this.by
      let l = this.cx
      let m = this.dw
      
      this.atService.getStatic(this.date,this.date1,this.from,this.to,"thread").subscribe(function (data5) { 
        //console.log("ud"+ a);
        this.data6 = data5
        //  console.log(JSON.stringify(dat))
        //console.log(data1.results.length)
        
        data5.results.map(function(results) {
            // results.map(function(series) {
            //    series.values.map(function(eachValueArray) {
            //       console.log(eachValueArray)
            //    })
            // })
            
            results.series.map(function(e) {
              //console.log(e.values)
              var c1=0,c2=0,c3=0,c4=0;
              e.values.map(function(eachValueArray) {
                console.log(eachValueArray)
                
                eachValueArray.map(function(value, index) {
                   //console.log(value, index)
                   if (index === 0) {
            //        console.log(value,"bta");  
                    h.push(parseMillisecondsIntoReadableTime(value))
                      // this.ud.push(value)
                   }else if(index ===6) {
                    //console.log(value,"bta bhai");
                     i.push(value)
                      // this.gg.push(value)
                   }
                   else if(index ===5){
                     if(value === 'RUNNABLE'){
                       c1++;
                       //console.log('state1',value);
                     }
                     else if(value === 'WAITING'){
                       c2++;
                       //console.log('state2',value);
                     }
                     else if(value === 'BLOCKED'){
                       c3++;
                       //console.log('state3',value);
                     }
                     else{
                       c4++;
                       //console.log('state4',value);
                     }
                   }
                   
                })
                j.push(c1);
                k.push(c2);
                l.push(c3);
                m.push(c4);
              //  console.log(c1,c2,c3,c4)
                
              })
              // this.ngOnDestroy()
              
            })
            //console.log(results.series)
        })
        // this.popData();
        
        
      });
      
      //console.log(b);
      //console.log(c);
      //console.log(d);
      //console.log(g);
      //console.log(f);
      // this.ngOnDestroy();
  
      this.lineChartData2 = [
        // {data: b, label: 'Series A'},
        {data: j, label: 'RUNNABLE'},
        {data: k, label: 'WAITING'},
        {data: l, label: 'BLOCKED'},
        {data: m, label: 'TIMED_WAITING'}
        ];
        //console.log(this.lineChartData);
        //console.log(a);
       this.lineChartLabels2 = h;
       //console.log(this.lineChartData);
     

      

      }
      ngOnDestroy() {
        this.subscription.unsubscribe();
        clearInterval(this.interval);
    }

    public lineChartColors2:Array<any> = [
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