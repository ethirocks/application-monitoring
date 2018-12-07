import {NgModule} from '@angular/core';
import {RouterModule,Routes} from '@angular/router';
import { CpuMetricStatComponent } from '../cpu-metric-stat/cpu-metric-stat.component';

import { AdventureTimeService } from '../../real-time/front-end1/services/adventure-time.service';
import { ChartsModule } from 'ng2-charts';
import { ThreadListStatComponent } from '../thread-list-stat/thread-list-stat.component';
import { NetworkStatComponent } from '../network-stat/network-stat.component';
import { HttpStatComponent } from '../http-stat/http-stat.component';
import { CpuUsageStatComponent } from '../cpu-usage-stat/cpu-usage-stat.component';
import { CpuCoresStatComponent } from '../cpu-cores-stat/cpu-cores-stat.component';
import { RamStatComponent } from '../ram-stat/ram-stat.component';
import { BarChartStatComponent } from '../bar-chart-stat/bar-chart-stat.component';
import { HealthMetricStatComponent } from '../health-metric-stat/health-metric-stat.component';
import { StaticComponent } from '../static/static.component';
// import { DisplayComponent } from '../display/display.component';

const routes:Routes = [
   
    {path: 'network-stat/:date/:date1/:from/:to', component:NetworkStatComponent},
    {path: 'thread-list-stat/:date/:date1/:from/:to', component:ThreadListStatComponent},
    {path: 'http-stat/:date/:date1/:from/:to', component:HttpStatComponent},
    {path: 'cpuusage-stat/:date/:date1/:from/:to', component:CpuUsageStatComponent},
    {path: 'cputemp-stat/:date/:date1/:from/:to', component:CpuMetricStatComponent},
    {path: 'memory-stat/:date/:date1/:from/:to', component:RamStatComponent},
    {path: 'disk_utilization-stat/:date/:date1/:from/:to', component:BarChartStatComponent},
    {path: 'cpucores-stat/:date/:date1/:from/:to', component:CpuCoresStatComponent},
    {path: 'health-stat/:date/:date1/:from/:to', component:HealthMetricStatComponent}
    
    // {path:'cpu-cores',component:CpuCoresComponent},
    ];

@NgModule({
    imports:[
        RouterModule.forRoot(routes),
        ChartsModule
    ],
    exports:[
        RouterModule
    ],
    providers: [AdventureTimeService]
})

export class StaticRoutingModule{};

export const routingComponents1 = [StaticComponent,NetworkStatComponent,ThreadListStatComponent,HttpStatComponent,CpuMetricStatComponent,CpuUsageStatComponent,RamStatComponent,BarChartStatComponent,HealthMetricStatComponent,CpuCoresStatComponent];