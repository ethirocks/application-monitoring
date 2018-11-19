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
    // this.columns = this.atService.getColumns();
    // //["name", "age", "species", "occupation"]
    // this.characters = this.atService.getCharacters();
    // //all data in mock-data.ts

    this.atService.getDetails("thread").subscribe((data:any) =>{ this.thread = data
      // console.log("error msg " + this.errorMsg);
      // console.log("theraasds..  "+this.thread);
      // console.log(this.thread);
      this.res = this.thread.results;
    });


    //  this.atService.getDetails()
    //       .pipe(map((data : any) => data.results.values))
    //       .subscribe((data:any) => this.atService = data)

  }

}