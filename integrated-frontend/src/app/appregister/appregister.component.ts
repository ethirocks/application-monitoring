import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, NgForm, FormControl } from '@angular/forms';
import { Dash } from '../dash';
import { DashService } from '../dash.service';
import { TokenStorageService } from '../login/auth/token-storage.service';
import { Router } from "@angular/router";
import { ApplicationServiceService } from '../application-service.service';

export interface Dependency {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-appregister',
  templateUrl: './appregister.component.html',
  styleUrls: ['./appregister.component.css']
})
export class AppregisterComponent implements OnInit {

  dependencies: Dependency[] = [
    {value: 'jar', viewValue: 'JAR'},
    {value: 'war', viewValue: 'WAR'},
    {value: 'docker', viewValue: 'DOCKER'},
    {value: 'nodeJs', viewValue: 'NodeJS'}
  ];

  dash: Dash = new Dash();

  constructor(private formBuilder: FormBuilder,
    private dashBack: DashService,
    private tokenstorageservice: TokenStorageService,
    private router: Router) { }
  ngOnInit() {

  }

  submitted = false;

  onSubmit() {
    this.submitted = true;
    if (this.submitted = true) {
      this.router.navigate([`/dashboard`]);
    }
  }

dashCast:Dash=new Dash();

type: string;

  addapplicationregister(dash: Dash) {
    this.dashCast = {
      "uid": Number(this.tokenstorageservice.getUid()),
      "dependency": this.type,
      "appname": this.dash.appname,
      "ipaddress": this.dash.ipaddress
    }
    console.log(this.dashCast)
    this.dashBack.addDashboard(this.dashCast).subscribe();
  }

  applicationregisterform = new FormGroup({
    applicationnameform: new FormControl(),
    ipadressform: new FormControl(),
    dependencyform: new FormControl()
  });

  storeDependency(value: string){
    this.type=value;
    console.log(this.type);
  }

}
