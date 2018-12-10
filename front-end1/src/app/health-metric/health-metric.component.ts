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

    onResize() {
      this.myFunction();
    }
    ud:string='';
    private myFunction(){
      var str = this.health.results.map(d3 => d3.series.map(d3 => d3.values));
      this.ud= str[0][0][0][1];
      return this.ud;
    }
  }


