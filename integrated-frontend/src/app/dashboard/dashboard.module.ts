import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ViewAllApplicationsComponent } from './view-all-applications/view-all-applications.component';
import { LiveDataComponent } from './live-data/live-data.component';
import { StaticDataComponent } from './static-data/static-data.component';
import { ApplicationCardComponent } from './application-card/application-card.component';
import { SamplingComponent } from './sampling/sampling.component';
import { SampleDialogComponent } from './sample-dialog/sample-dialog.component';
import { MatButtonModule, MatFormFieldModule, MatInputModule, MatRippleModule, MatIconModule } from '@angular/material';
import { MyDialogComponent } from '../modals/my-dialog/my-dialog.component';
import { MyDialog1Component } from '../modals/my-dialog1/my-dialog1.component';
import { MyDialog2Component } from '../modals/my-dialog2/my-dialog2.component';
import { MyDialog3Component } from '../modals/my-dialog3/my-dialog3.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    DashboardComponent,
    MyDialogComponent,
    MyDialog1Component,
    MyDialog2Component,
    MyDialog3Component,
    ViewAllApplicationsComponent,
    LiveDataComponent,
    StaticDataComponent,
    ApplicationCardComponent,
    SamplingComponent,
    SampleDialogComponent,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule,
    MatIconModule
  ],
  exports: [
    DashboardComponent,
    MyDialogComponent,
    MyDialog1Component,
    MyDialog2Component,
    MyDialog3Component,
    ViewAllApplicationsComponent,
    LiveDataComponent,
    StaticDataComponent,
    ApplicationCardComponent
  ],
  entryComponents:[
    SampleDialogComponent
  ]
})
export class DashboardModule { }
