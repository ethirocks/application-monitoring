import { Injectable } from '@angular/core';

const TOKEN_KEY = 'AuthToken';
const EMAIL_KEY = 'AuthEMAIL';
const UID_KEY= 'AuthUID';
@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor() { }
  signOut() {
    localStorage.clear();
  }
  public saveToken(token: string) {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }
  public saveEmail(email: string) {
    localStorage.removeItem(EMAIL_KEY);
    localStorage.setItem(EMAIL_KEY, email);
  }
  public getEmail(): string {
    return localStorage.getItem(EMAIL_KEY);
  }
  public saveUid(uid: string) {
    localStorage.removeItem(UID_KEY);
    localStorage.setItem(UID_KEY, uid);
  }
  public getUid(): string {
    return localStorage.getItem(UID_KEY);
  }
}
