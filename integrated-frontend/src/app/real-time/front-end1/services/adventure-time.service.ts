import { HealthModel } from './../health-data/health.model';
import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { ActivatedRoute } from '@angular/router';

@Injectable()
export class AdventureTimeService {

  constructor(private http: HttpClient,
    private route: ActivatedRoute) { 
    }

  private _url : string = "https://appmonitoring-zuul.stackroute.in/monitoring/api/v1/metrics/";
  data : any = {};
  health : HealthModel;
  getDetails(metricsName:string,userID:Number,applicationID:Number){
    console.log("check");
    console.log(this._url+metricsName+"?userID="+userID+"&applicationID="+applicationID);
    console.log("raed"+this.http.get<any>(this._url+metricsName+"?userID="+userID+"&applicationID="+applicationID));
      return this.http.get<any>(this._url+metricsName+"?userID="+userID+"&applicationID="+applicationID);
  }

  getStatic(date:string,date1:string,from: number, to: number, metric: string){
    // console.log("fuck you",from);
    console.log(this._url+"query/"+" SELECT * FROM "+metric+" WHERE time > "+"'"+date+" "+from+":00"+"'"+" AND time <= "+"'"+date1+" "+to+":00"+"'");
    return this.http.get<any>(this._url+"query/"+" SELECT * FROM "+metric+" WHERE time > "+"'"+date+" "+from+":00"+"'"+" AND time <= "+"'"+date1+" "+to+":00"+"'");
    
  }

}