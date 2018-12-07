import { ActivatedRoute } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DockerService{
  

  constructor(private http: HttpClient,
    private route: ActivatedRoute) { 
    }
  private _url : string = "https://appmonitoring-zuul.stackroute.in/monitoring/api/v1/metrics/";
  data : any = {};
  // health : HealthModel;
  getDetails(metricsName:string,userID:Number,applicationID:Number){
    console.log(applicationID);
    console.log("check");
    console.log(this._url+"query/select * from "+ metricsName +" where userID='"+userID+"' and applicationID='"+applicationID+"' order by time desc limit 1");
    console.log("raed"+this.http.get<any>(this._url+"query/"+ "select * from"+ metricsName +" where userID="+userID+" and applicationID="+applicationID));
      // return this.http.get<any>(this._url+"query/select * from "+ metricsName +" where userID='"+userID+"' and applicationID='"+applicationID+"'");

      return this.http.get<any>(this._url+"query/select * from "+ metricsName +" where userID='"+userID+"' and applicationID='"+applicationID+"' order by time desc limit 1");
      // SELECT * FROM <SERIES> GROUP BY * ORDER BY DESC LIMIT 1
  }

}
