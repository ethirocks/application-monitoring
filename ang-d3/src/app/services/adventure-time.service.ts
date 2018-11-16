import { HttpClient } from '@angular/common/http';
import { ThreadDataModel } from './../thread-data/thread-data.model';

import { Injectable } from '@angular/core';

import { of } from 'rxjs';

import { Observable } from 'rxjs';
// import 'rxjs/add/observable/of';
// import 'rxjs/add/operator/delay';
import { CHARACTERS } from './data1';

@Injectable()
export class AdventureTimeService {

  constructor(private http: HttpClient) { }


  private _url : string = "http://localhost:8080/api/v1/metrics/";
  data : any = {};

  getDetails(metricsName:string): Observable<any>{
    console.log("check");
    console.log(this._url+metricsName);
    console.log("raed"+this.http.get<any>(this._url+metricsName));
      return this.http.get<any>(this._url+metricsName);
  }

  

  getCharacters(): Observable<any[]>{
    return of(CHARACTERS);
  }

  getColumns(): string[]{
    return ["time", "thread_group", "thread_name", "thread_priority","thread_status","timeStamp","total_Threads"]
  }

}
