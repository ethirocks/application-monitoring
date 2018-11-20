import { HealthModel } from './../health-data/health.model';
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


  private _url : string = "http://52.66.184.4:8080/api/v1/metrics/";
  data : any = {};
  health : HealthModel;

  getDetails(metricsName:string): Observable<any>{
    console.log("check");
    console.log(this._url+metricsName);
    console.log("raed"+this.http.get<any>(this._url+metricsName));
      return this.http.get<any>(this._url+metricsName);
  }

//  getFunc(){
//   var str = this.health.results.map(d3 => d3.series.map(d3 => d3.values));
//   var ud= str[0][0][0][3];
//   console.log(ud);
//   return ud;
//  } 

  getCharacters(): Observable<any[]>{
    return of(CHARACTERS);
  }

  getColumns(): string[]{
    return ["time", "thread_group", "thread_name", "thread_priority","thread_status","timeStamp","total_Threads"]
  }

}