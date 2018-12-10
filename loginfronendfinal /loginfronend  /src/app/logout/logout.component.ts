import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/login/auth/token-storage.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  info: any;
  constructor(private token: TokenStorageService) { }

  ngOnInit() {
    this.info = this.token.getEmail();
  }
  logout() {
    this.token.signOut();
    window.location.reload();
  }
}
