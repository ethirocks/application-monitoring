import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-my-dialog',
  templateUrl: './my-dialog.component.html',
  styleUrls: ['./my-dialog.component.css']
})
export class MyDialogComponent implements OnInit {

 constructor(public thisDialogRef: MatDialogRef<MyDialogComponent>) { }

  ngOnInit() {
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }
}
