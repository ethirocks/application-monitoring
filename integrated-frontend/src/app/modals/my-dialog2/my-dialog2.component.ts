import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';
@Component({
  selector: 'app-my-dialog2',
  templateUrl: './my-dialog2.component.html',
  styleUrls: ['./my-dialog2.component.css']
})
export class MyDialog2Component implements OnInit {

  constructor(public thisDialogRef: MatDialogRef<MyDialog2Component>) { }

  ngOnInit() {
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }

}
