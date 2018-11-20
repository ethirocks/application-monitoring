import { CpuDataModel } from '../health-data/cpu.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit } from '@angular/core';
import * as d3 from 'd3';
import {  ElementRef, Input, OnChanges, ViewChild, ViewEncapsulation, HostListener, SystemJsNgModuleLoader } from '@angular/core';

@Component({
  selector: 'app-cpu-usage',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './cpu-usage.component.html',
  styleUrls: ['./cpu-usage.component.css'],
  
})
export class CpuUsageComponent implements OnInit {
  @ViewChild('chart')
  private chartContainer: ElementRef;

  constructor(private atService : AdventureTimeService) { }

  margin = {top: 20, right: 30, bottom: 30, left: 50};
  width = 960 - this.margin.left - this.margin.right;
  height = 600 - this.margin.top - this.margin.bottom;

  res : any;

  @Input()
  container : CpuDataModel;
  

  ngOnInit() {
    this.atService.getDetails("cpuusage").subscribe((data:any) =>{ this.container = data
      this.res = this.container.results;
    });
  }
}