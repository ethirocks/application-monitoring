import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NodejsService {

  constructor(private http: HttpClient,
    private route: ActivatedRoute) { 
    }
  private _url : string = "https://appmonitoring-zuul.stackroute.in/monitoring/api/v1/metrics/";
  data : any = {};
  // health : HealthModel;
  getDetails(metricsName:string,insideMetrics:string,userID:Number,applicationID:Number){
    console.log(applicationID);
    console.log("check");
    console.log(this._url+"query/"+"select last("+insideMetrics+") from "+ metricsName +" where userID='"+userID+"' and applicationID='"+applicationID+"'");
    console.log("raed"+this.http.get<any>(this._url+"query/"+ "select last(insideMetrics) from"+ metricsName +" where userID="+userID+" and applicationID="+applicationID));
      return this.http.get<any>(this._url+"query/select last("+insideMetrics+") from "+ metricsName +" where userID='"+userID+"' and applicationID='"+applicationID+"'");
  }
} 
