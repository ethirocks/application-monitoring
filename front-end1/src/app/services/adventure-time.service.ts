import { HealthModel } from './../health-data/health.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable()
export class AdventureTimeService {

  constructor(private http: HttpClient) { }


  private _url : string = "http://52.66.184.4:8080/api/v1/metrics/";
  data : any = {};
  health : HealthModel;

  getDetails(metricsName:string){
    console.log("check");
    console.log(this._url+metricsName);
    console.log("raed"+this.http.get<any>(this._url+metricsName));
      return this.http.get<any>(this._url+metricsName);
  }



}