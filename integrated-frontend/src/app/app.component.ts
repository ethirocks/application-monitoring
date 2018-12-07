import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from './login/auth/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'loginfrontend';
  constructor(tokenStorageService: TokenStorageService , router: Router) {
  if (tokenStorageService.getToken()) {
    router.navigate(['/dashboard']);
  }  else {
    router.navigate(['/home']);
  }
}
}
