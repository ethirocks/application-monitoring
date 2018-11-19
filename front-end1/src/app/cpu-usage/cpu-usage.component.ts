import { CpuDataModel } from '../health-data/cpu.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cpu-usage',
  templateUrl: './cpu-usage.component.html',
  styleUrls: ['./cpu-usage.component.css']
})
export class CpuUsageComponent implements OnInit {

  constructor(private atService : AdventureTimeService) { }

  container : CpuDataModel;
  res : any;

  ngOnInit() {
    this.atService.getDetails("cpuusage").subscribe((data:any) =>{ this.container = data
      // console.log("error msg " + this.errorMsg);
      //  console.log("usage  "+this.container);
      // console.log(this.container);
      this.res = this.container.results;
      //this.myFunction();
       //this.r = this.res.series;
      // this.s = this.r.values;
      
      //console.log("results"+this.res);
    });
  }

}
