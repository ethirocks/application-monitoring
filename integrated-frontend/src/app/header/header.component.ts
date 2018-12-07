import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../login/auth/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isloggedin= false;
  constructor( private token: TokenStorageService) { }
  ngOnInit() {
     if(this.token.getToken())
      {
        this.isloggedin=true;
      }
  }
  logout() {
    this.token.signOut();
    window.location.reload();
  }
}
