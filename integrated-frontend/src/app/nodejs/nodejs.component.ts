import { NodejsService } from './../nodejs.service';
import { ActivatedRoute } from '@angular/router';
import { DockerService } from './../docker.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nodejs',
  templateUrl: './nodejs.component.html',
  styleUrls: ['./nodejs.component.css']
})
export class NodejsComponent implements OnInit {
  userID: any;
  applicationID: any;
  constructor(private _nodejs: NodejsService, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      this.userID = params['userID'];
      this.applicationID = params['applicationID'];

    });
  }
  cpu;
  res: any;
  mem: any;
  temp: any;
  systemcpu: any;
  totalmem: any;
  ngOnInit(): void {
    this._nodejs.getDetails("nodeCPU", "cpu", this.userID, this.applicationID).subscribe((data1: any) => {
      // this.res=data1,
      this.cpu = data1.results
      console.log("cpuin", this.cpu)
    }),
      this._nodejs.getDetails("nodeCPU", "systemCpu", this.userID, this.applicationID).subscribe((data2: any) => {
        // this.res=data2,
        this.systemcpu = data2.results
      }),
      this._nodejs.getDetails("nodeMemory", "memory", this.userID, this.applicationID).subscribe((data3: any) => {
        // this.res=data3,
        this.mem = data3.results
      }),
      this._nodejs.getDetails("nodeMemory", "totalMemory", this.userID, this.applicationID).subscribe((data4: any) => {
        // this.res=data4,
        this.totalmem = data4.results
      }),
      this._nodejs.getDetails("nodeTemperature", "temperature", this.userID, this.applicationID).subscribe((data5: any) => {
        // this.res=data5,
        this.temp = data5.results
      });


  }



  // }

  getdata() {
    this._nodejs.getDetails("nodeCPU", "cpu", this.userID, this.applicationID).subscribe((data1: any) => {
      // this.res=data1,
      this.cpu = data1.results
      console.log("cpuin", this.cpu)
    }),
      this._nodejs.getDetails("nodeCPU", "systemCpu", this.userID, this.applicationID).subscribe((data2: any) => {
        // this.res=data2,
        this.systemcpu = data2.results
      }),
      this._nodejs.getDetails("nodeMemory", "memory", this.userID, this.applicationID).subscribe((data3: any) => {
        // this.res=data3,
        this.mem = data3.results
      }),
      this._nodejs.getDetails("nodeMemory", "totalMemory", this.userID, this.applicationID).subscribe((data4: any) => {
        // this.res=data4,
        this.totalmem = data4.results
      }),
      this._nodejs.getDetails("nodeTemperature", "temperature", this.userID, this.applicationID).subscribe((data5: any) => {
        // this.res=data5,
        this.temp = data5.results
      });
  }

  // data1.results.map(function(results) {
  //     results.series.map(function(e) {
  //      console.log("e",e.values)
  //       e.values.map(function(eachValueArray) {
  //         //  this.res = eachValueArray
  //       // console.log("values",this.res )
  //         })
  //       })

  //     })
  //console.log("temp"+result.series);

}

