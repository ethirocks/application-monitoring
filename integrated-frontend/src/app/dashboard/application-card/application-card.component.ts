import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { IApplication } from '../../IApplication';
import { ApplicationServiceService } from '../../application-service.service';
import { TokenStorageService } from '../../login/auth/token-storage.service';
import { Router } from '@angular/router';
import { IValues } from '../../IValues';
import { Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { SampleDialogComponent } from '../sample-dialog/sample-dialog.component';

@Component({
  selector: 'app-application-card',
  templateUrl: './application-card.component.html',
  styleUrls: ['./application-card.component.css']
})
export class ApplicationCardComponent implements OnInit {

  constructor(private _applicationService: ApplicationServiceService,
    private tokenstorageservice: TokenStorageService,
    private _router: Router,
    public dialog: MatDialog) {
  }
  ngOnInit() {
  }
  @Input() application: IApplication;
  @Input() item: String;

  sampleResponse: String;
  sampleDataResponse: String;
  // userID=Number(this.tokenstorageservice.getUid());
  movieExists;

  over: String;

  query: String;

  meanValue: IValues;
  maxValue: IValues;
  minValue: IValues;
  countValue: IValues;

  sample(samplingPeriod) {
    this.over = "Your Application is Sampling....";
    this._applicationService.sampleApplication(samplingPeriod, this.application)
      .subscribe(data => this.over = data, error => this.sampleResponse = "error");
    //  location.reload();
  }

  getColumnName(metricName: String): String {
    var columnName: String;
    if (metricName === 'cpuusage') {
      columnName = 'cpu_usage';
    }
    else if (metricName === 'cputemp') {
      columnName = 'cpu_temp';
    }
    else if (metricName === 'cpuCores') {
      columnName = 'cpu_cores';
    }
    else if (metricName === 'disk_utilization') {
      columnName = 'details_diskFree';
    }
    else if (metricName === 'health') {
      columnName = 'status';
    }
    else if (metricName === 'http_requests_agent_Sample') {
      columnName = 'ResponseTime';
    }
    else if (metricName === 'memory') {
      columnName = 'free_physical_memory';
    }
    else if (metricName === 'networkMetricsSample') {
      columnName = 'Upload_Speed';
    }
    return columnName;
  }
  // 
  viewSampleData(metricsName: String) {
    const dialogRef = this.dialog.open(SampleDialogComponent, {
      width: '400px',
      data: { application: this.application }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      //  this.animal = result;
    });

    var metricName = metricsName;
    console.log(metricName);
    var columnName = this.getColumnName(metricName);

    this.query = "select count(" + this.getColumnName(metricName) + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "'";
    this._applicationService.getSampleData(this.query).subscribe(data => this.countValue = data,
      error => this.sampleDataResponse = "error");
    console.log("countValue", this.countValue);

    this.query = "select mean(" + columnName + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "'";
    this._applicationService.getSampleData(this.query).subscribe(data => this.meanValue = data,
      error => this.sampleDataResponse = "error");
    console.log("meanValue", this.meanValue);

    this.query = "select min(" + columnName + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "'";
    this._applicationService.getSampleData(this.query).subscribe(data => this.minValue = data,
      error => this.sampleDataResponse = "error");
    console.log("minValue", this.minValue);

    this.query = "select max(" + columnName + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "'";
    this._applicationService.getSampleData(this.query).subscribe(data => this.maxValue = data,
      error => this.sampleDataResponse = "error");
    console.log("maxValue", this.maxValue);

  }

  staticData() {
    this._router.navigate(['../static', this.application.userId, this.application.id]);
  }

  monitor() {
    this._router.navigate(['../display', this.application.userId, this.application.id]);
  }

}