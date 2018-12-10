import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, EMPTY, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { IApplication } from './IApplication';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ApplicationServiceService {
  constructor(private http: HttpClient) { }

  getApplications(id: Number): Observable<any> {
    return this.http.get("https://appmonitoring-zuul.stackroute.in/dashboard/appdetails?i="+id).pipe(
      catchError(this.errorHandler));
  }

  getSampleData(query: String): Observable<any> {
    return this.http.get("https://appmonitoring-zuul.stackroute.in/alert/sampleData/"+query).pipe(
      catchError(this.errorHandler));
  }

  sampleApplication(interval:Number, application:IApplication): Observable<any> {
    return this.http.post("https://appmonitoring-zuul.stackroute.in/sampling/api/v1/samplingserver?interval="+interval,application,httpOptions).pipe(
      catchError(this.errorHandler));
  }

  pollApplication(interval:Number, application:IApplication): Observable<any> {
    return this.http.post("https://appmonitoring-zuul.stackroute.in/monitoring/poll",application,httpOptions).pipe(
      catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || "Server error");
  }
}
