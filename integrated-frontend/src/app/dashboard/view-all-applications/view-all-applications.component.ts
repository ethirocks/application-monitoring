import { Component, OnInit } from '@angular/core';
import { ApplicationServiceService } from '../../application-service.service';
import { TokenStorageService } from '../../login/auth/token-storage.service';
import {MatDialog} from '@angular/material';
import { MyDialog2Component } from '../../modals/my-dialog2/my-dialog2.component';
import { MyDialogComponent } from '../../modals/my-dialog/my-dialog.component';
import { MyDialog3Component } from '../../modals/my-dialog3/my-dialog3.component';

import { MyDialog1Component } from '../../modals/my-dialog1/my-dialog1.component';

@Component({
  selector: 'app-view-all-applications',
  templateUrl: './view-all-applications.component.html',
  styleUrls: ['./view-all-applications.component.css']
})
export class ViewAllApplicationsComponent implements OnInit {
  constructor(private _applicationService: ApplicationServiceService,
    private tokenstorageservice: TokenStorageService,public dialog: MatDialog) { }
  userID=Number(this.tokenstorageservice.getUid());
  page="";
  public applicationsList;
  item="view";
  event = { request: "", value: "",appID: 0 };
  openDialog() {
    let dialogRef = this.dialog.open(MyDialog2Component,{
      width: '30vw',
      height:'70vh' });
  }
  ngOnInit() {
    this._applicationService.getApplications(this.userID).subscribe(data => this.applicationsList = data, error => this.applicationsList = "No applications available");
  }
  // recieveEvent($event) {
  //   this.event = $event;
  //   this.update(this.event);
  //   console.log(this.event);
  // }

  // update(event) {
  //   if (event.request === "sample") {
  //     this.page="sample";
  //   }
  //   else if (event.request === "static") {
  //     this.page="static";
  //   }
  //   else if (event.request === "monitor") {
  //    this.page="monitor";
  //   }
  // }
}