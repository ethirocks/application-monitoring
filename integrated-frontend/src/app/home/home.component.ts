import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/login/auth/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor() { }

  breakpoint: number;

  ngOnInit() {
    this.breakpoint = (window.innerWidth >= 601) ? 3 : 300;
  }

  onResize(event) {
    this.breakpoint = (event.target.innerWidth <= 600) ? 3 : 1;
  }
}
