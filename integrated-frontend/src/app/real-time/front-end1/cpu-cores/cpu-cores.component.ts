import { AdventureTimeService } from './../services/adventure-time.service';
import { CpuDataModel } from '../health-data/cpu.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-cpu-cores',
  templateUrl: './cpu-cores.component.html',
  styleUrls: ['./cpu-cores.component.css']
})
export class CpuCoresComponent implements OnInit {


  gg : any = [];
  ud : any = [];
  lineChartData:Array<any>;
  lineChartLabels:Array<any>;
  str: any;

  userID;
  appID;
  a;  
  constructor(private atService: AdventureTimeService,
    private route: ActivatedRoute) { 
      this.route.params.subscribe( params => {
        this.userID =params['userID'];
        this.appID = params['appID'];
      } );
    }

  container : CpuDataModel;
  res : any;

  ngOnInit() {

    console.log("ud"+this.gg);
    let a = this.ud
    let b = this.gg


    this.atService.getDetails("cpuCores",this.userID,this.appID).subscribe((data:any) =>{ this.container = data
      
      this.res = this.container.results;
      this.str = data.results.map(d3 => d3.series.map(d3 => d3.values));
      // for(var i = 0; i< 5 ;i++){
      //     this.ud[i]= this.str.values;
      //     console.log("ud"+this.ud[i]);
      // }

      parseMillisecondsIntoReadableTime(this.str[0][0][0][0]);
    });
  }
     
}
      
//       console.log("ud"+ a);
//       this.data = data1
//       //  console.log(JSON.stringify(dat))
//       console.log(data1.results.length)
//       data1.results.map(function(results) {
//           // results.map(function(series) {
//           //    series.values.map(function(eachValueArray) {
//           //       console.log(eachValueArray)
//           //    })
//           // })
//           results.series.map(function(e) {
//             console.log(e.values)
//             e.values.map(function(eachValueArray) {
//               console.log(eachValueArray)
//               eachValueArray.map(function(value, index) {
//                  console.log(value, index)
//                  if (index === 0) {
//                     a.push(value)
//                     // this.ud.push(value)
//                  }else {
//                    b.push(value)
//                     // this.gg.push(value)
//                  }
//               })
//             })
            
//           })
//           console.log(results.series)
//       })
//        //console.log(this.data);
//       //  this.val=data.results.series[0].values;
       
//       // this.res = this.data;
//       //console.log("ppppppppp"+this.data.results);
//       // this.str = data1.results.map(d3 => d3.series.map(d3 => d3.values));
//       // for(var i = 0; i< 5 ;i++){
//       //     this.ud[i]= this.str.values;
//       //     console.log("ud"+this.ud[i]);
//       // }
//       // console.log("values :"+this.str[0][0][0][1]);
//     //  this.gg = this.str.values.map(o=>o.temp);
//     // this.gg=this.str[0][0][1][1];
//     //  this.gg = this.str.values.map(o => o.temp);
//     // console.log("fhjhshfj",this.gg);
//       // this.createChart();
//       // this.func();


     
     
      

      

//     });
//     // console.log("sjfhjhsjh",this.str[0][0][1][1]);

    function parseMillisecondsIntoReadableTime(milliseconds){
      //Get hours from milliseconds
      var hours = milliseconds / (1000*60*60);
      var absoluteHours = Math.floor(hours);
      var h = absoluteHours > 9 ? absoluteHours : '0' + absoluteHours;
    
      //Get remainder from hours and convert to minutes
      var minutes = (hours - absoluteHours) * 60;
      var absoluteMinutes = Math.floor(minutes);
      var m = absoluteMinutes > 9 ? absoluteMinutes : '0' +  absoluteMinutes;
    
      //Get remainder from minutes and convert to seconds
      var seconds = (minutes - absoluteMinutes) * 60;
      var absoluteSeconds = Math.floor(seconds);
      var s = absoluteSeconds > 9 ? absoluteSeconds : '0' + absoluteSeconds;
    
    
      return m + ':' + s;
    }

    
//     this.lineChartData = [
//       {data: b, label: 'Series A'},
//       ];
//      this.lineChartLabels = a;
   

//   }


  
  

//   // public createChart(){

//   //   const data = this.contain;
//   //   const i = 5;
     
//   //    console.log("llll"+this.ud);

//   // }

//   //  func(){
//   //   console.log("daaataaa"+this.str[0][0][0][0]);
//   // }
  

//   // lineChart
// //  public lineChartData:Array<any> = [
// //     {data: [this.gg], label: 'Series A'},
// //     {data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B'},
// //     {data: [18, 48, 77, 9, 100, 27, 40], label: 'Series C'}
// //   ];
// //   public lineChartLabels:Array<any> = [1,2,3,4,5];
//   public lineChartOptions:any = {
//     responsive: true
//   };
//   public lineChartColors:Array<any> = [
//     { // grey
//       backgroundColor: 'rgba(148,159,177,0.2)',
//       borderColor: 'rgba(148,159,177,1)',
//       pointBackgroundColor: 'rgba(148,159,177,1)',
//       pointBorderColor: '#fff',
//       pointHoverBackgroundColor: '#fff',
//       pointHoverBorderColor: 'rgba(148,159,177,0.8)'
//     },
//     { // dark grey
//       backgroundColor: 'rgba(77,83,96,0.2)',
//       borderColor: 'rgba(77,83,96,1)',
//       pointBackgroundColor: 'rgba(77,83,96,1)',
//       pointBorderColor: '#fff',
//       pointHoverBackgroundColor: '#fff',
//       pointHoverBorderColor: 'rgba(77,83,96,1)'
//     },
//     { // grey
//       backgroundColor: 'rgba(148,159,177,0.2)',
//       borderColor: 'rgba(148,159,177,1)',
//       pointBackgroundColor: 'rgba(148,159,177,1)',
//       pointBorderColor: '#fff',
//       pointHoverBackgroundColor: '#fff',
//       pointHoverBorderColor: 'rgba(148,159,177,0.8)'
//     }
//   ];
//   public lineChartLegend:boolean = true;
//   public lineChartType:string = 'line';
 
//   // public randomize():void {
//   //   let _lineChartData:Array<any> = new Array(this.lineChartData.length);
//   //   for (let i = 0; i < this.lineChartData.length; i++) {
//   //     _lineChartData[i] = {data: new Array(this.lineChartData[i].data.length), label: this.lineChartData[i].label};
//   //     for (let j = 0; j < this.lineChartData[i].data.length; j++) {
//   //       _lineChartData[i].data[j] = Math.floor((Math.random() * 100) + 1);
//   //     }
//   //   }
//   //   this.lineChartData = _lineChartData;
//   // }
 
//   // events
//   public chartClicked(e:any):void {
//     console.log(e);
//   }
 
//   public chartHovered(e:any):void {
//     console.log(e);
//   }
     
    
//   }


