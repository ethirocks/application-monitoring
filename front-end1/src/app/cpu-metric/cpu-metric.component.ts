
import { map } from 'rxjs/operators';
import { LineData, threadValues ,bar} from './../health-data/cpu.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit, ElementRef, Input, OnDestroy } from '@angular/core';
import { CpuDataModel } from '../health-data/cpu.model';
import { Subscription } from 'rxjs';
import * as d3 from 'd3';
import { symbolTriangle } from 'd3';

@Component({
  selector: 'app-cpu-metric',
  templateUrl: './cpu-metric.component.html',
  styleUrls: ['./cpu-metric.component.css']
})
export class CpuMetricComponent implements OnInit, OnDestroy {

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
  linedata : LineData[];
  ud : any = [];
  data: CpuDataModel;
  data4: CpuDataModel;
  str: bar;
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


  constructor(private atService : AdventureTimeService, private container : ElementRef) {   
  }

  @Input()
  contain : CpuDataModel;

  ngOnInit() {
    this.refreshData();
    this.interval = setInterval(() => { 
        this.refreshData(); 
    }, 1000);
    
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    clearInterval(this.interval);
}

  refreshData() {
    let t = this.ud
    let p = this.gg
    let q = this.aa
    let r = this.bb


    this.atService.getDetails("http_requests").subscribe(function (data8) { 
      this.data = data8
      data8.results.map(function(results) {
          results.series.map(function(e) {
            e.values.map(function(eachValueArray) {
              eachValueArray.map(function(value, index) {
                 if (index === 0) {
                  t.push(parseMillisecondsIntoReadableTime(value))
                 }else if(index ===1) {
                   p.push(value)
                 }
                 else if(index ===2){
                   q.push(value)
                 }
                 else if(index === 6){
                   r.push(value)
                 }
                 
              })
            })
            
            
          })
         
      })
      
    });

    this.lineChartData5 = [
      {data: p, label: 'Data-IN'},
      {data: q, label: 'Data-OUT'},
      {data: r, label: 'Response-TIME'},
      ];
     this.lineChartLabels5 = t;
     
   
    let a = this.ud
    let b = this.gg
    let c = this.aa
    let d = this.bb
    
    this.atService.getDetails("cputemp").subscribe(function (data1) { 
      this.data = data1
      data1.results.map(function(results) {
          results.series.map(function(e) {
            e.values.map(function(eachValueArray) {
              eachValueArray.map(function(value, index) {
                 if (index === 0) {
                    a.push(parseMillisecondsIntoReadableTime(value))
                 }else {
                   b.push(value)
                 }
              })
            })
            
          })
         
      })
         });
    

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

    
    this.lineChartData = [
      {data: b, label: 'CPU TEMPERATURE'},
      ];
    
     this.lineChartLabels = a;
   

     this.atService.getDetails("cpuusage").subscribe(function (data2) { 
      this.data = data2
      data2.results.map(function(results) {
          
          results.series.map(function(e) {
            e.values.map(function(eachValueArray) {
              eachValueArray.map(function(value, index) {
                 if (index === 0) {
                    c.push(parseMillisecondsIntoReadableTime(value))
                 }else {
                   d.push(value)
                 }
              })
            })
            
          })
      })
      
    });

    this.lineChartData1 = [
                  
                  {data: d, label: 'CPU USAGE', borderDash : [5,5] },
      ];
     this.lineChartLabels1 = c;


     let e = this.ee;
     let f = this.ff;
     let g = this.gh;
     
     this.atService.getDetails("disk_utilization").subscribe(function (data3) { 
       this.data4 = data3
       data3.results.map(function(results) {
           results.series.map(function(i) {
             i.values.map(function(eachValueArray) {
               eachValueArray.map(function(value, index) {
                  if (index === 0) {
                     e.push(parseMillisecondsIntoReadableTime(value))
                  }else if(index === 1){
                    f.push(value)
                  }else {
                    g.push(value)
                  }
               })
             })
             
           })
       })
       
 
     });
   
     this.barChartData = [
       {data: f, label: 'DiskFree'},
       {data: g, label: 'DiskTotal'}
       ];
      this.barChartLabels = e;

      let h = this.xx
      let i = this.yy
      let j = this.az
      let k = this.by
      let l = this.cx
      let m = this.dw
      
      this.atService.getDetails("thread").subscribe(function (data5) { 
        this.data6 = data5
        data5.results.map(function(results) {
            results.series.map(function(e) {
              var c1=0,c2=0,c3=0,c4=0;
              e.values.map(function(eachValueArray) {
                eachValueArray.map(function(value, index) {
                   if (index === 0) {
                    console.log(value,"bta");  
                    h.push(parseMillisecondsIntoReadableTime(value))
                   }else if(index ===6) {
                     i.push(value)
                   }
                   else if(index ===4){
                     if(value === 'RUNNABLE'){
                       c1++;
                     }
                     else if(value === 'WAITING'){
                       c2++;
                     }
                     else if(value === 'BLOCKED'){
                       c3++;
                     }
                     else{
                       c4++;
                     }
                   }
                   
                })
                j.push(c1);
                k.push(c2);
                l.push(c3);
                m.push(c4);
                
              })
              
              
            })
            
        })
        
      });
     
      this.lineChartData2 = [
        {data: j, label: 'RUNNABLE'},
        {data: k, label: 'WAITING'},
        {data: l, label: 'BLOCKED'},
        {data: m, label: 'TIMED_WAITING'}
        ];
       this.lineChartLabels2 = h;

       let u = this.uu

       let v = this.vv
   
       let w = this.ww
   
       let x = this.bb
   
       let y = this.cc
   
       let z = this.dd
   
       this.atService.getDetails("networkMetrics").subscribe(function (data1) { 
   
         this.data = data1
  
         data1.results.map(function(results) {
   
             results.series.map(function(e) {
   
               e.values.map(function(eachValueArray) {
   
                 eachValueArray.map(function(value, index) {

                    if (index === 0) {
   
                     u.push(parseMillisecondsIntoReadableTime(value))
   
                    }else if(index === 3) {
   
                      v.push(value)
 
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
   
   
         })
   
       });
   
  
       this.lineChartData7 = [
   
         {data: v, label: 'Downlaod Speed'},
   
         {data: w, label: 'Upload Speed'},
   
         {data: x, label: 'Data in rate'},
   
         {data: y, label: 'Data out rate'},
   
         {data: z, label: 'Max_transmission_rate'},
   
         ];

   
        this.lineChartLabels7 = u;
  
    }


    public lineChartColors2:Array<any> = [
      { 
        borderColor: 'lightgreen',
      },
      { 
        borderColor: 'skyblue',
       
      },
      { 
        borderColor: 'yellow',
      },
      { 
        borderColor: 'red',
       
      }
    ];

  public lineChartColors7:Array<any> = [

    { 
      borderColor: 'red',
    },

    { 

      borderColor: 'green',

    },

    { 
      borderColor: 'blue',

    },

    { 
      borderColor: 'yellow',
    },

    { 

      borderColor: 'black',
    }

  ];
    public lineChartColors5:Array<any> = [
     
      { 
        borderColor: 'lightgreen',
   
      },
      { 
        borderColor: 'skyblue',
        
      },
      { 
        borderColor: 'yellow',
        
      },
      { 
        borderColor: 'red',
      
      }
    ];

    public lineChartColors1:Array<any> = [
      { 
        backgroundColor: 'white',
        borderColor:  'green',
        pointBackgroundColor: 'brown',
        pointBorderColor: 'red',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'coral',
        pointStyle: 'rectRounded',
        pointRadius: 8
      }]


  public lineChartColors:Array<any> = [
    { 
      backgroundColor: 'white',
      borderColor:  'coral',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { 
      backgroundColor: 'rgba(77,83,96,0.2)',
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
  public lineChartLegend:boolean = true;
  public lineChartType:string = 'line';
  public lineChartOptions: any = {
    resposive : true,
 
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
    animation: false,
    scaleOverride: true,
    scaleSteps: 10,
    scaleStepWidth: 10,
    scaleStartValue: 0
  }
  
  
  public barChartType:string = 'bar';
  public barChartLegend:boolean = true;
  }

  
  

  

 


