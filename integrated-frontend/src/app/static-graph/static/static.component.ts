import { Component, OnInit, Input } from '@angular/core';
import { FormControl, FormGroup, FormBuilder, Validators} from '@angular/forms'
import { toDate } from '@angular/common/src/i18n/format_date';
// import { AdventureTimeService } from '../services/adventure-time.service';
// import { Chart } from 'canvasjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-static',
  templateUrl: './static.component.html',
  styleUrls: ['./static.component.css']
})
export class StaticComponent implements OnInit{

  date:any;
  date1 : any
  from:any;
  to:any;
  metric_name="";
  data1:any;
  
  ud : any = [];
  cc : any = [];
  gg : any = [];
  barChartData:Array<any>;
  barChartLabels:Array<any>;

  constructor(private router: Router) { }

  ngOnInit() {
    
  }
  displaym(){

    let a = this.ud
    let b = this.gg
    let c = this.cc

    console.log(this.date);
    console.log(this.from);
    console.log(this.to);

    var f = new Date(this.date +" " +this.from);
    var t = new Date(this.date+" "+this.to);
    //var ft = f.getTime();
    //console.log(ft,"d");
    //var tt = t.getTime();
    //console.log('bata bhai',tt);
    console.log('metric-name',this.metric_name);

    // this.atService.getStatic(ft,tt,this.metric_name).subscribe(function(data1){
    //   this.data=data1;
    //   data1.results.map(function(results){
    //     results.series.map(function(e){
    //       e.values.map(function(eachValueArray){
    //         eachValueArray.map(function(value,index){
    //           if(index === 0){
    //                 a.push(value)
    //           }else if(index === 1){
    //             b.push(value)
    //           }
    //         })
    //       })
    //     })
    //   })

    // })
    

    // this.barChartData = [
    //   {data: b, label: 'DiskFree'}
      
    //   ];
    //  this.barChartLabels = a;

    console.log(this.metric_name)
    if(this.metric_name === "network"){
         this.router.navigate(['/network-stat',this.date,this.date1,this.from,this.to]);
    }else if(this.metric_name === "thread"){
         this.router.navigate(['/thread-list-stat',this.date,this.date1,this.from,this.to]);
    }else if(this.metric_name === "cpuusage"){
         this.router.navigate(['/cpuusage-stat',this.date,this.date1,this.from,this.to]);
    }else if(this.metric_name === "cputemp"){
         this.router.navigate(['/cputemp-stat',this.date,this.date1,this.from,this.to]);
    }else if(this.metric_name === "disk_utilization"){
         this.router.navigate(['/disk_utilization-stat',this.date,this.date1,this.from,this.to]);
    }else if(this.metric_name === "http"){
        this.router.navigate(['/http-stat',this.date,this.date1,this.from,this.to]);
    }else if(this.metric_name === "memory"){
        this.router.navigate(['/memory-stat',this.date,this.date1,this.from,this.to]);
    }else if(this.metric_name === "cpucores"){
      this.router.navigate(['/cpucores-stat',this.date,this.date1,this.from,this.to]);
    }else if(this.metric_name === "health"){
      this.router.navigate(['/health-stat',this.date,this.date1,this.from,this.to]);
    }
    

  }


}
