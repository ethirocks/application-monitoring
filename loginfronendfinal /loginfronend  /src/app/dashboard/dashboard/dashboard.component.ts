import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators, NgForm, FormControl } from '@angular/forms';
import {MatDialog} from '@angular/material'; 
import { MyDialogComponent } from '../../my-dialog/my-dialog.component';
import { MyDialog1Component } from '../../my-dialog1/my-dialog1.component';
import { MyDialog2Component } from '../../my-dialog2/my-dialog2.component';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {


  constructor(public dialog: MatDialog) { }
  openDialog() {
    let dialogRef = this.dialog.open(MyDialogComponent,{
      width: '30vw',
      height:'40vh'    });
  }
  openDialog1() {
    let dialogRef = this.dialog.open(MyDialog1Component,{
      width: '30vw',
      height:'40vh'    });
  }
  openDialog2() {
    let dialogRef = this.dialog.open(MyDialog2Component,{
      width: '30vw',
      height:'40vh'    });
  }

  ngOnInit() {

  } 

  onSubmit() { 
  }

}