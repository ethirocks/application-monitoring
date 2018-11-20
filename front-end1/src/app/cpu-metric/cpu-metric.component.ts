import { LineData } from './../health-data/cpu.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit, ElementRef, Input } from '@angular/core';
import { CpuDataModel } from '../health-data/cpu.model';
import * as d3 from 'd3';

@Component({
  selector: 'app-cpu-metric',
  templateUrl: './cpu-metric.component.html',
  styleUrls: ['./cpu-metric.component.css']
})
export class CpuMetricComponent implements OnInit {

  

 
  dataSource : object;
  chartConfig: Object;
  res : any;
  svg : any;
  margin = {top: 5, right: 5, bottom: 5, left: 5};
  width : number;
  height : number;
  g : any;
  x : any;
  y : any;
  valueline : any;
  linedata : LineData[];

  constructor(private atService : AdventureTimeService, private container : ElementRef) {   
  }

  @Input()
  contain : CpuDataModel;

  ngOnInit() {

    this.atService.getDetails("cputemp").subscribe((data:any) =>{ this.contain = data
      this.res = this.contain.results;
    });
  }
}
