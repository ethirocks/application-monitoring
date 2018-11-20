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
      this.res = this.container.results;
    });
  }

}
