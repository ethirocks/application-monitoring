import {NgModule} from '@angular/core';
import {RouterModule,Routes, ActivatedRoute} from '@angular/router';
import { CpuMetricComponent } from '../cpu-metric/cpu-metric.component';

import { AdventureTimeService } from '../services/adventure-time.service';
import { ChartsModule } from 'ng2-charts';
import { ThreadListComponent } from '../thread-list/thread-list.component';
import { NetworkComponent } from '../network/network.component';
import { HttpComponent } from '../http/http.component';
import { CpuUsageComponent } from '../cpu-usage/cpu-usage.component';
import { CpuCoresComponent } from '../cpu-cores/cpu-cores.component';
import { RamComponent } from '../ram/ram.component';
import { BarChartComponent } from '../bar-chart/bar-chart.component';
import { HealthMetricComponent } from '../health-metric/health-metric.component';
import { StaticComponent } from '../static/static.component';
import { DisplayComponent } from '../display/display.component';

const childroutes:Routes = [
    {path: 'cpu-metric/:userID/:appID' , component: CpuMetricComponent},
    {path: 'cpu-usage/:userID/:appID' , component : CpuUsageComponent},
    {path : 'cpu-cores/:userID/:appID' , component : CpuCoresComponent},
    {path : 'health-metric/:userID/:appID' , component : HealthMetricComponent},
    {path : 'http/:userID/:appID' , component: HttpComponent},
    {path : 'thread-list/:userID/:appID' , component : ThreadListComponent},
    {path: 'network/:userID/:appID' , component: NetworkComponent},
    {path : 'ram/:userID/:appID' , component: RamComponent}
    
    // {path: 'display', component: DisplayComponent},
    // {path: 'static', component: StaticComponent} 
    
    // {path:'cpu-cores',component:CpuCoresComponent},
    ];

@NgModule({
    imports:[
        RouterModule.forRoot(childroutes),
        ChartsModule
    ],
    exports:[
        RouterModule
    ],
    providers: [AdventureTimeService]
})

export class DisplayRoutingModule{};

export const routingComponent = [ThreadListComponent,CpuMetricComponent,NetworkComponent,HttpComponent,CpuUsageComponent,RamComponent,BarChartComponent,CpuCoresComponent,HealthMetricComponent];