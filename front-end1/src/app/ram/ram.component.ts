import { CpuDataModel } from '../health-data/cpu.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ram',
  templateUrl: './ram.component.html',
  styleUrls: ['./ram.component.css']
})
export class RamComponent implements OnInit {

  constructor(private atService : AdventureTimeService) { }

  container : CpuDataModel;
  res : any;

  ngOnInit() {
    this.atService.getDetails("memory").subscribe((data:any) =>{ this.container = data
      this.res = this.container.results;
    });
  }

}
