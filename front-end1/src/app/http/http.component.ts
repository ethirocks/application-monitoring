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
      this.res = this.http_requests.results;
    });
  }

}
