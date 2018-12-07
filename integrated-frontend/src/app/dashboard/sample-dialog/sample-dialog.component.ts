import { Component, OnInit } from '@angular/core';
import { Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { IApplication } from '../../IApplication';
import { IValues } from '../../IValues';
import { ApplicationServiceService } from '../../application-service.service';

export interface DialogData {
  application: IApplication
}

@Component({
  selector: 'app-sample-dialog',
  templateUrl: './sample-dialog.component.html',
  styleUrls: ['./sample-dialog.component.css']
})
export class SampleDialogComponent implements OnInit {

  application: IApplication;
  item: String;


  constructor(
    public dialogRef: MatDialogRef<SampleDialogComponent>,
    private _applicationService: ApplicationServiceService,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
    this.application = data.application;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  // view(item: String){
  //   this.item=item;
  // }

  ngOnInit() {
  }


  sampleResponse: String;
  sampleDataResponse: String;

  query: String;

  meanValue: IValues;
  maxValue: IValues;
  minValue: IValues;
  countValue: IValues;

  column: string;

  getColumnName(metricName: String): String {
    var columnName: String;
    if (metricName === 'cpuusage') {
      columnName = 'cpu_usage';
      this.column = "CPU usage";
    }
    else if (metricName === 'cputemp') {
      columnName = 'cpu_temp';
      this.column = "CPU temperature";
    }
    else if (metricName === 'cpuCores') {
      columnName = 'cpu_cores';
      this.column = "CPU cores"
    }
    else if (metricName === 'disk_utilization') {
      columnName = 'details_diskFree';
      this.column = "available space in HardDisk"
    }
    else if (metricName === 'health') {
      columnName = 'status';
      this.column = "status of application";
    }
    else if (metricName === 'http_requests_agent_Sample') {
      columnName = 'ResponseTime';
      this.column = "response time of http requests"
    }
    else if (metricName === 'memory') {
      columnName = 'free_physical_memory';
      this.column = "free memory available"
    }
    else if (metricName === 'networkMetricsSample') {
      columnName = 'Upload_Speed';
      this.column = "upload speed of all networks"
    }
    else if (metricName === 'nodeCPU') {
      columnName = 'cpu';
      this.column = "cpu memory used by your application"
    }
    else if (metricName === 'nodeMemory') {
      columnName = 'free_memory';
      this.column = "memory used by your system"
    }
    else if (metricName === 'nodeTemperature') {
      columnName = 'temp';
      this.column = "temperature of your system"
    }
    else if (metricName === 'warCpuCoresSample') {
      columnName = 'cpu_cores';
      this.column = " number of cpu cores of your sytem"
    }
    else if (metricName === 'warCpuTemp') {
      columnName = 'cpu_temp';
      this.column = "CPU temperature of your system"
    }
    else if (metricName === 'warCpuUsage') {
      columnName = 'cpu_usage';
      this.column = "cpu usage"
    }
    else if (metricName === 'warNetworkMetricsSample') {
      columnName = 'Upload_Speed';
      this.column = "upload speed of all networks"
    }
    else if (metricName === 'warMemory') {
      columnName = 'free_physical_memory';
      this.column = "free physical memory of your system"
    }
    else if (metricName === 'Container') {
      columnName = 'cpu';
      this.column = "free physical memory of your system"
    }
    return columnName;
  }

  content="Sampling data not available. Please Sample your application.";
  content1;
  content2;
  content3;
  content4;

  view(metricsName: String) {

    var metricName = metricsName;
    console.log(metricName);
    var columnName = this.getColumnName(metricName);

    if (metricsName === 'health') {
      this.query = "select count(" + this.getColumnName(metricName) + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "'";
      this._applicationService.getSampleData(this.query).subscribe(data => this.countValue = data,
        error => this.sampleDataResponse = "error");
      console.log("countValue", this.countValue);

      this.query = "select count(" + this.getColumnName(metricName) + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "' and "+columnName+"='UP'";
      this._applicationService.getSampleData(this.query).subscribe(data => this.meanValue = data,
        error => this.sampleDataResponse = "error");
      console.log("meanValue", this.meanValue);
      if(this.meanValue){
        var percent=(this.meanValue.value/this.countValue.value)*100;
      }
      this.content = this.column;
      this.content1 = "Total number of samples = " + this.countValue.value + ".";
      this.content2 = "Your application status is UP at "+percent+"% time of the time during sampling.";
      this.content3 = "";
      this.content4 = "";
    }
    else {
      this.query = "select count(" + this.getColumnName(metricName) + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "'";
      console.log(this.query);
      this._applicationService.getSampleData(this.query).subscribe(data => this.countValue = data,
        error => this.sampleDataResponse = "error");
      console.log("countValue", this.countValue);

      this.query = "select mean(" + this.getColumnName(metricName) + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "'";
      this._applicationService.getSampleData(this.query).subscribe(data => this.meanValue = data,
        error => this.sampleDataResponse = "error");
      console.log("meanValue", this.meanValue);

      this.query = "select min(" + this.getColumnName(metricName) + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "'";
      this._applicationService.getSampleData(this.query).subscribe(data => this.minValue = data,
        error => this.sampleDataResponse = "error");
      console.log("minValue", this.minValue);

      this.query = "select max(" + this.getColumnName(metricName) + ") from " + metricName + " where applicationID='" + this.application.id + "' and userID='" + this.application.userId + "'";
      this._applicationService.getSampleData(this.query).subscribe(data => this.maxValue = data,
        error => this.sampleDataResponse = "error");
      console.log("maxValue", this.maxValue);

      var minDate = new Date(1324339200000);
      var maxDate = new Date(1324339200000);

      // this.content="Total number of samples = "+this.countValue.value+".  /n"+
      //               "Average value of "+this.column+" = "+this.meanValue.value+".  \n"+
      //               "Minimum value of "+this.column+" = "+this.minValue.value+". It occured at "+minDate+" \n"+
      //               "Maximum value of "+this.column+" = "+this.maxValue.value+". It occured at "+maxDate+" \n";
      this.content = this.column;
      this.content1 = "Total number of samples = " + this.countValue.value + ".";
      this.content2 = "Average value of " + this.column + " = " + this.meanValue.value + ".";
      this.content3 = "Minimum value of " + this.column + " = " + this.minValue.value + ". It occured at " + minDate + ".";
      this.content4 = "Maximum value of " + this.column + " = " + this.maxValue.value + ". It occured at " + maxDate + ".";

    }
  }

}
