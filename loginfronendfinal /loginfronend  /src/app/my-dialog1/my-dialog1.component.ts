import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';
@Component({
  selector: 'app-my-dialog1',
  templateUrl: './my-dialog1.component.html',
  styleUrls: ['./my-dialog1.component.css']
})
export class MyDialog1Component implements OnInit {

  constructor(public thisDialogRef: MatDialogRef<MyDialog1Component>) { }

  ngOnInit() {
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }

}
