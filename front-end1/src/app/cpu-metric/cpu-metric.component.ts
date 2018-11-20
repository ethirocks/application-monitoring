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
      this.res = this.container.results;
    });
  }

}
