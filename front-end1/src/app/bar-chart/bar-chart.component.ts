import { map } from 'rxjs/operators';
//import { threadValues, results } from './../thread-data/thread-data.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { DataModel } from '../health-data/data.model';
import { Component, ElementRef, Input, OnChanges, ViewChild, ViewEncapsulation, HostListener, SystemJsNgModuleLoader, OnInit } from '@angular/core';
import * as d3 from 'd3';
//import * as d3 from 'd3-selection';
import * as d3Scale from 'd3-scale';
import * as d3Shape from 'd3-shape';
import * as d3Array from 'd3-array';
import * as d3Axis from 'd3-axis';
import { timeParse } from 'd3';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
//import { BarChartComponent} from './bar-chart.component.spec';

//import { DataModel } from 'src/app/data/data.model';

@Component({
  selector: 'app-bar-chart',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss'],
})
export class BarChartComponent implements OnInit {
  @ViewChild('chart')
  private chartContainer: ElementRef;

  @Input()
  bar: DataModel;

  margin = {top: 20, right: 30, bottom: 30, left: 50};
  width = 150 - this.margin.left - this.margin.right;
  height = 600 - this.margin.top - this.margin.bottom;

  

  
  constructor(private atService : AdventureTimeService) { }

 
  errorMsg : string;
  res : any;
  x : any;
  y : any;
  svg : any;
  d : any;
  v : any;
  data : any[];
  line: d3Shape.Line<[number, number]>;

  ngOnInit() {
    this.atService.getDetails("disk_utilization").subscribe((data:any) =>{ this.bar = data
      this.res = this.bar.results;
    }); 
  }
}




