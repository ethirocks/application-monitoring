import { Component, OnInit } from '@angular/core';
import { AdventureTimeService } from '../../real-time/front-end1/services/adventure-time.service';
import { ActivatedRoute } from '@angular/router';
import { CpuDataModel } from '../../real-time/front-end1/health-data/cpu.model';

@Component({
  selector: 'app-war-cores',
  templateUrl: './war-cores.component.html',
  styleUrls: ['./war-cores.component.css']
})
export class WarCoresComponent implements OnInit {

  gg : any = [];
  ud : any = [];
  lineChartData:Array<any>;
  lineChartLabels:Array<any>;
  str: any;

  userID;
  appID;
  a;  
  constructor(private atService: AdventureTimeService,
    private route: ActivatedRoute) { 
      this.route.params.subscribe( params => {
        this.userID =params['userID'];
        this.appID = params['appID'];
      } );
    }

  container : CpuDataModel;
  res : any;


  ngOnInit() {
    
    console.log("ud"+this.gg);
    let a = this.ud
    let b = this.gg


    this.atService.getDetails("warCpuCores",this.userID,this.appID).subscribe((data:any) =>{ this.container = data
      
      this.res = this.container.results;
      this.str = data.results.map(d3 => d3.series.map(d3 => d3.values));
      // for(var i = 0; i< 5 ;i++){
      //     this.ud[i]= this.str.values;
      //     console.log("ud"+this.ud[i]);
      // }

      parseMillisecondsIntoReadableTime(this.str[0][0][0][0]);
    });
  }
     


  }
  function parseMillisecondsIntoReadableTime(milliseconds){
    //Get hours from milliseconds
    var hours = milliseconds / (1000*60*60);
    var absoluteHours = Math.floor(hours);
    var h = absoluteHours > 9 ? absoluteHours : '0' + absoluteHours;
  
    //Get remainder from hours and convert to minutes
    var minutes = (hours - absoluteHours) * 60;
    var absoluteMinutes = Math.floor(minutes);
    var m = absoluteMinutes > 9 ? absoluteMinutes : '0' +  absoluteMinutes;
  
    //Get remainder from minutes and convert to seconds
    var seconds = (minutes - absoluteMinutes) * 60;
    var absoluteSeconds = Math.floor(seconds);
    var s = absoluteSeconds > 9 ? absoluteSeconds : '0' + absoluteSeconds;
  
  
    return h + ':' + m + ':' + s;
  }

