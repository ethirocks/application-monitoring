import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit } from '@angular/core';
import { HealthModel } from '../health-data/health.model';

@Component({
  selector: 'app-health-metric',
  templateUrl: './health-metric.component.html',
  styleUrls: ['./health-metric.component.css']
})
export class HealthMetricComponent implements OnInit {

  health: HealthModel ;
  errorMsg: string;
  res : any;
  r : any;
  s : any;
  v : any;
  arr : string[];
  constructor(private atService : AdventureTimeService) { }


  ngOnInit() {
    
    this.atService.getDetails("health").subscribe((data:any) =>{ this.health = data
      this.res = this.health.results;
    });
  }
}