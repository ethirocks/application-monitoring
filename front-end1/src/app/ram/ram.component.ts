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
      // console.log("error msg " + this.errorMsg);
      //  console.log("kkkkk..  "+this.container);
      // console.log(this.container);
      this.res = this.container.results;
      //this.myFunction();
       //this.r = this.res.series;
      // this.s = this.r.values;
      
      //console.log("results"+this.res);
    });
  }

}
