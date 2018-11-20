import { AdventureTimeService } from './../services/adventure-time.service';
import { CpuDataModel } from '../health-data/cpu.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})
export class NetworkComponent implements OnInit {

  constructor(private atService : AdventureTimeService) { }

  container : CpuDataModel;
  res : any;


  ngOnInit() {

    this.atService.getDetails("network").subscribe((data:any) =>{ this.container = data
      this.res = this.container.results;
    });
  }

}
