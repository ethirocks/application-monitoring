import { ThreadDataModel, thread, threadValues } from './../thread-data/thread-data.model';

import { CHARACTERS } from './../bar-chart/thrd';
import { AdventureTimeService } from './../services/adventure-time.service';
import { BarChartComponent } from './../bar-chart/bar-chart.component';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators'

@Component({
  selector: 'app-thread-list',
  templateUrl: './thread-list.component.html',
  styleUrls: ['./thread-list.component.css']
})
export class ThreadListComponent implements OnInit {

  constructor(private atService: AdventureTimeService) { }


  characters: Observable<any[]>;
  columns: string[];
  threadList:ThreadDataModel;
  thread: ThreadDataModel ;
  errorMsg: string;
  res : any;

  ngOnInit() {

    this.atService.getDetails("thread").subscribe((data:any) =>{ this.thread = data
      this.res = this.thread.results;
    });
<<<<<<< HEAD
}
=======



  }

>>>>>>> 43e94e3a0bcfdd3c4a3fc814337586a2a947fdee
}