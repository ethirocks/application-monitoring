import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, NgForm, FormControl } from '@angular/forms';
import { Dash } from '../dash';
import { DashService } from '../dash.service';
import { TokenStorageService } from '../login/auth/token-storage.service';
import { Router } from "@angular/router";
import { ApplicationServiceService } from '../application-service.service';


@Component({
  selector: 'app-appregister',
  templateUrl: './appregister.component.html',
  styleUrls: ['./appregister.component.css']
})
export class AppregisterComponent implements OnInit {

  dash: Dash = new Dash();

  constructor(private formBuilder: FormBuilder,
    private dashBack: DashService,
    private tokenstorageservice: TokenStorageService,
    private router: Router) { }
  ngOnInit() {

  }

  submitted = false;
  public message = "Successfully registered";

  onSubmit() {
    this.submitted = true;
    alert(this.message);
    if (this.submitted = true) {
      this.router.navigate([`/dashboard`]);
    }
  }

  addapplicationregister(dash: Dash) {
    var dashCast = {
      "uid": Number(this.tokenstorageservice.getUid()),
      "dependency": this.dash.dependency,
      "appname": this.dash.appname,
      "ipaddress": this.dash.ipaddress
    }
    this.dashBack.addDashboard(dashCast).subscribe();
  }

  applicationregisterform = new FormGroup({
    applicationnameform: new FormControl(),
    ipadressform: new FormControl(),
    dependencyform: new FormControl()
  });

}
