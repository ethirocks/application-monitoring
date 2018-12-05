import { HttpModel } from './../health-data/http.model';
import { CpuDataModel } from '../health-data/cpu.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-http',
  templateUrl: './http.component.html',
  styleUrls: ['./http.component.css']
})
export class HttpComponent implements OnInit {

  constructor(private atService: AdventureTimeService) { }

  http_requests: HttpModel;
  res : any;

  ngOnInit() {

    this.atService.getDetails("http_requests").subscribe((data:any) =>{ this.http_requests = data
      // console.log("error msg " + this.errorMsg);
        console.log("kkkkk..  "+this.http_requests);
      console.log(this.http_requests);
      this.res = this.http_requests.results;
      //this.myFunction();
       //this.r = this.res.series;
      // this.s = this.r.values;
      
      //console.log("results"+this.res);
    });
  }

}
