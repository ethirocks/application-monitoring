import { Component, OnInit, Input, ElementRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { AdventureTimeService } from '../../real-time/front-end1/services/adventure-time.service';
import { ActivatedRoute } from '@angular/router';
import { CpuDataModel, LineData, threadValues, bar } from '../../real-time/front-end1/health-data/cpu.model';
import { TokenStorageService } from '../../login/auth/token-storage.service';

@Component({
  selector: 'app-war-usage',
  templateUrl: './war-usage.component.html',
  styleUrls: ['./war-usage.component.css']
})
export class WarUsageComponent implements OnInit {

  private subscription: Subscription = new Subscription();



  dataSource: object;
  chartConfig: Object;
  res: any;
  svg: any;
  margin = { top: 5, right: 5, bottom: 5, left: 5 };
  width: number;
  height: number;
  gg: any = [];
  aa: any = [];
  bb: any = [];
  x: any;
  y: any;
  dd: any = [];
  cc: any = [];
  uu: any = [];
  vv: any = [];
  ww: any = [];
  ee: any = [];
  ff: any = [];
  gh: any = [];
  az: any = [];
  by: any = [];
  cx: any = [];
  dw: any = [];
  xx: any = [];
  yy: any = [];
  valueline: any;
  linedata: LineData[];
  // str: CpuDataModel;
  ud: any = [];
  // contain: any;
  data: CpuDataModel;
  data4: CpuDataModel;
  str: bar;
  charts: any = [];
  lineChartData: Array<any>;
  lineChartLabels: Array<any>;
  lineChartData1: Array<any>;
  lineChartLabels1: Array<any>;
  barChartData: Array<any>;
  barChartLabels: Array<any>;
  lineChartData2: Array<any>;
  lineChartLabels2: Array<any>;
  lineChartData5: Array<any>;
  lineChartLabels5: Array<any>;
  lineChartData7: Array<any>;
  lineChartLabels7: Array<any>;
  interval: any;
  // lineChartOptions:any = [];

  userID;
  appID;
  a;


  constructor(private atService: AdventureTimeService,
    private container: ElementRef,
    private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      this.userID = params['userID'];
      this.appID = params['appID'];
    });
  }

  @Input()
  contain: CpuDataModel;

  ngOnInit() {
    let count = 0;
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
    // let t = this.ud
    // let p = this.gg
    // let q = this.aa
    // let r = this.bb


    // this.atService.getDetails("http_requests").subscribe(function (data8) { 
    //   //console.log("ud"+ a);
    //   this.data = data8
    //   //  console.log(JSON.stringify(dat))
    //   //console.log(data1.results.length)

    //   data8.results.map(function(results) {
    //       // results.map(function(series) {
    //       //    series.values.map(function(eachValueArray) {
    //       //       console.log(eachValueArray)
    //       //    })
    //       // })

    //       results.series.map(function(e) {
    //         //console.log(e.values)
    //         var c1=0,c2=0,c3=0,c4=0;
    //         e.values.map(function(eachValueArray) {
    //           //console.log(eachValueArray)

    //           eachValueArray.map(function(value, index) {
    //              //console.log(value, index)
    //              if (index === 0) {
    //               console.log(value,"bta");  
    //               t.push(parseMillisecondsIntoReadableTime(value))
    //                 // this.ud.push(value)
    //              }else if(index ===1) {
    //               console.log(value,"bta bhai");
    //                p.push(value)
    //                 // this.gg.push(value)
    //              }
    //              else if(index ===2){
    //                console.log(value)
    //                q.push(value)
    //              }
    //              else if(index === 6){
    //               console.log(value)
    //                r.push(value)
    //              }

    //           })
    //           // c.push(c1);
    //           // d.push(c2);
    //           // g.push(c3);
    //           // f.push(c4);
    //           // console.log(c1,c2,c3,c4)

    //         })


    //       })
    //       //console.log(results.series)
    //   })

    // });

    // this.lineChartData5 = [
    //   // {data: b, label: 'Series A'},
    //   {data: p, label: 'Data-IN'},
    //   {data: q, label: 'Data-OUT'},
    //   {data: r, label: 'Response-TIME'},
    //   // {data: f, label: 'TIMED_WAITING'}
    //   ];
    //   //console.log(this.lineChartData);
    //   //console.log(a);
    //  this.lineChartLabels5 = t;
    //  //console.log(this.lineChartData);







    // let a = this.ud
    // let b = this.gg
    let c = this.aa
    let d = this.bb

    // this.atService.getDetails("cputemp").subscribe(function (data1) { 
    //   //console.log("ud"+ a);
    //   this.data = data1
    //   //  console.log(JSON.stringify(dat))
    //   //console.log(data1.results.length)
    //   data1.results.map(function(results) {
    //       results.series.map(function(e) {
    //     //    console.log(e.values)

    //         e.values.map(function(eachValueArray) {
    //       //    console.log(eachValueArray)
    //           eachValueArray.map(function(value, index) {
    //         //     console.log(value, index)
    //              if (index === 0) {
    //                 a.push(parseMillisecondsIntoReadableTime(value))
    //                 // this.ud.push(value)

    //              }else if(index === 1){
    //                b.push(value)
    //                 // this.gg.push(value)

    //              }
    //           })
    //         })

    //       })
    //       //console.log(results.series)
    //   })
    //      });


    function parseMillisecondsIntoReadableTime(milliseconds) {
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


      // return m + ':' + s;

      var date = new Date(milliseconds);

      console.log(date.toString());

      return date.toLocaleTimeString();
    }

    this.atService.getDetails("warCpuUsage", this.userID, this.appID).subscribe(function (data2) {
      //console.log("ud"+ a);
      this.data = data2
      //  console.log(JSON.stringify(dat))
      //console.log(data2.results.length)
      data2.results.map(function (results) {

        results.series.map(function (e) {
          //    console.log(e.values)
          e.values.map(function (eachValueArray) {
            //    console.log(eachValueArray)
            eachValueArray.map(function (value, index) {
              //     console.log(value, index)
              if (index === 0) {
                c.push(parseMillisecondsIntoReadableTime(value))
                if (c.length > 150) {
                  c.splice(0, 1)
                }
                // this.ud.push(value)
              } else if (index === 2) {
                d.push(value)
                if (d.length > 150) {
                  d.splice(0, 1)
                }
                // this.gg.push(value)
              }
            })
          })

        })
        //console.log(results.series)
      })

    });

    this.lineChartData1 = [

      { data: d, label: 'CPU USAGE', borderDash: [5, 5] },
    ];
    this.lineChartLabels1 = c;

  }

  public lineChartColors2: Array<any> = [

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
      borderColor: 'red'
    }
  ];





  public lineChartColors7: Array<any> = [

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
  public lineChartColors5: Array<any> = [
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

  public lineChartColors1: Array<any> = [
    { // grey
      backgroundColor: 'white',
      borderColor: 'green',//'rgba(148,159,177,1)',
      pointBackgroundColor: 'brown',//'rgba(148,159,177,1)',
      pointBorderColor: 'red',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'coral',//'rgba(148,159,177,0.8)',
      pointStyle: 'circle',
      pointRadius: 2
    }]


  public lineChartColors: Array<any> = [
    { // grey
      backgroundColor: 'white',
      borderColor: 'coral',//'rgba(148,159,177,1)',
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
  public lineChartLegend: boolean = true;
  public lineChartType: string = 'line';
  public lineChartOptions: any = {
    resposive: false,





  }
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }

  public barChartOptions: any = {
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


  public barChartType: string = 'bar';
  public barChartLegend: boolean = true;




  // var myLineChart = new Chart(ctx).Line(data, options);


  // var months = ["January", "February", "March", "April", "May", "June",
  //   "July", "August", "September", "October", "November", "December"
  // ];


}








