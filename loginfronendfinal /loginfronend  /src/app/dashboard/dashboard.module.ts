import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MyDialogComponent } from '../my-dialog/my-dialog.component';
import { MyDialog1Component } from '../my-dialog1/my-dialog1.component';
import { MyDialog2Component } from '../my-dialog2/my-dialog2.component';

@NgModule({
  imports: [
    CommonModule],
  declarations: [DashboardComponent,MyDialogComponent,MyDialog1Component,MyDialog2Component]
})
export class DashboardModule { }
