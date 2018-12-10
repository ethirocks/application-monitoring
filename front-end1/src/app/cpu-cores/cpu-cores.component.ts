import { AdventureTimeService } from './../services/adventure-time.service';
import { CpuDataModel } from '../health-data/cpu.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cpu-cores',
  templateUrl: './cpu-cores.component.html',
  styleUrls: ['./cpu-cores.component.css']
})
export class CpuCoresComponent implements OnInit {


  gg : any = [];
  ud : any = [];
  lineChartData:Array<any>;
  lineChartLabels:Array<any>;
  str: any;


  constructor(private atService: AdventureTimeService) { }

  container : CpuDataModel;
  res : any;

  ngOnInit() {

    console.log("ud"+this.gg);
    let a = this.ud
    let b = this.gg

    this.atService.getDetails("cpuCores").subscribe((data:any) =>{ this.container = data
      
      this.res = this.container.results;
      this.str = data.results.map(d3 => d3.series.map(d3 => d3.values));
      parseMillisecondsIntoReadableTime(this.str[0][0][0][0]);
    });
  }
     
}
      

    function parseMillisecondsIntoReadableTime(milliseconds){
      var hours = milliseconds / (1000*60*60);
      var absoluteHours = Math.floor(hours);
      var h = absoluteHours > 9 ? absoluteHours : '0' + absoluteHours;
      var minutes = (hours - absoluteHours) * 60;
      var absoluteMinutes = Math.floor(minutes);
      var m = absoluteMinutes > 9 ? absoluteMinutes : '0' +  absoluteMinutes;
      var seconds = (minutes - absoluteMinutes) * 60;
      var absoluteSeconds = Math.floor(seconds);
      var s = absoluteSeconds > 9 ? absoluteSeconds : '0' + absoluteSeconds;
      return m + ':' + s;
    }


