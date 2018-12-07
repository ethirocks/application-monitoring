import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-my-dialog3',
  templateUrl: './my-dialog3.component.html',
  styleUrls: ['./my-dialog3.component.css']
})
export class MyDialog3Component implements OnInit {

  constructor(public thisDialogRef: MatDialogRef<MyDialog3Component>) { }

  ngOnInit() {
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }

}
