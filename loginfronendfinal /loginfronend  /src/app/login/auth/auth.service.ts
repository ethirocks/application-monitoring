import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { JwtResponse } from './jwt-response';
import { AuthLoginInfo } from './login-info';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _url = '/api/v1/login';
  constructor(private _http: HttpClient) { }
  submit(user: AuthLoginInfo): Observable<JwtResponse> {
    return this._http
      .post<JwtResponse>(this._url, user, httpOptions);
}
}
