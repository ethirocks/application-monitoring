import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css']
})
export class DisplayComponent {

  userID;
  appID;
  a;
  constructor(private route: ActivatedRoute) {
    // this.a = this.route.paramMap.pipe(
    //   switchMap(params => {
    //     this.userID = +params.get("userID");
    //     this.appID = +params.get("appID");
    //     return "";
    //   })
    // );
    this.route.params.subscribe( params => {
          this.userID =params['userID'];
          this.appID = params['appID'];
        } );
    console.log(this.userID + "...." + this.appID);
  }
  ngOnInit() {

  }
}



// children:[
//   {path:'thread-list',component:ThreadListComponent},
//   {path:'cpu-metric',component:CpuMetricComponent},
//   {path:'network',component:NetworkComponent},
//   {path:'http',component:HttpComponent},
//   {path:'cpu-usage',component:CpuUsageComponent},
//   {path:'ram',component:RamComponent},
//   {path:'bar-chart',component:BarChartComponent},
//   {path:'cpu-cores',component:CpuCoresComponent},
//   {path:'health-metric',component:HealthMetricComponent},
//  ]}
