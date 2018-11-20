import { HealthModel } from './../health-data/health.model';
import { HttpClient } from '@angular/common/http';
import { ThreadDataModel } from './../thread-data/thread-data.model';

import { Injectable } from '@angular/core';

import { of } from 'rxjs';

import { Observable } from 'rxjs';
import { CHARACTERS } from './data1';

@Injectable()
export class AdventureTimeService {

  constructor(private http: HttpClient) { }


  private _url : string = "http://52.66.184.4:8080/api/v1/metrics/";
  data : any = {};
  health : HealthModel;

  getDetails(metricsName:string): Observable<any>{
      return this.http.get<any>(this._url+metricsName);
  }


  getCharacters(): Observable<any[]>{
    return of(CHARACTERS);
  }

  getColumns(): string[]{
    return ["time", "thread_group", "thread_name", "thread_priority","thread_status","timeStamp","total_Threads"]
  }

}