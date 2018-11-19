import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit } from '@angular/core';
import { CpuDataModel } from '../health-data/cpu.model';

@Component({
  selector: 'app-cpu-metric',
  templateUrl: './cpu-metric.component.html',
  styleUrls: ['./cpu-metric.component.css']
})
export class CpuMetricComponent implements OnInit {

  constructor(private atService : AdventureTimeService) { }

  container : CpuDataModel;
  res : any;

  ngOnInit() {

    this.atService.getDetails("cputemp").subscribe((data:any) =>{ this.container = data
      // console.log("error msg " + this.errorMsg);
       console.log("kkkkk..  "+this.container);
      console.log(this.container);
      this.res = this.container.results;
      //this.myFunction();
       //this.r = this.res.series;
      // this.s = this.r.values;
      
      //console.log("results"+this.res);
    });
  }

}
