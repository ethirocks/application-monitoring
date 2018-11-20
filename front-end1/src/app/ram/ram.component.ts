import { CpuDataModel } from '../health-data/cpu.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit } from '@angular/core';
import {  ElementRef, Input, OnChanges, ViewChild, ViewEncapsulation, HostListener, SystemJsNgModuleLoader } from '@angular/core';
import * as d3 from 'd3';


@Component({
  selector: 'app-ram',
  templateUrl: './ram.component.html',
  styleUrls: ['./ram.component.scss'],
  
})
export class RamComponent implements OnInit {
  @ViewChild('chart')
  private chartContainer: ElementRef;

  constructor(private atService : AdventureTimeService) { }

  container : CpuDataModel;
  res : any;

  margin = {top: 20, right: 30, bottom: 30, left: 50};
  width = 960 - this.margin.left - this.margin.right;
  height = 600 - this.margin.top - this.margin.bottom;

 
  ngOnInit() {
    this.atService.getDetails("memory").subscribe((data:any) =>{ this.container = data
      this.res = this.container.results;
    });
  }
}