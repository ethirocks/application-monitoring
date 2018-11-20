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

<<<<<<< HEAD
    this.atService.getDetails("http_requests").subscribe((data:any) =>{ this.http_requests = data
      this.res = this.http_requests.results;
=======
    this.atService.getDetails("http_requests").subscribe((data:any) =>{ this.container = data
      this.res = this.container.results;
>>>>>>> 43e94e3a0bcfdd3c4a3fc814337586a2a947fdee
    });
  }

}
