import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes, ActivatedRoute } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login/login/login.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { RegisterComponent } from './register/register.component';
import { AppregisterComponent } from './appregister/appregister.component';
import { FooterComponent } from './footer/footer.component';
import { WarinfoComponent } from './info/warinfo/warinfo.component';
import { JarinfoComponent } from './info/jarinfo/jarinfo.component';
import { DockerinfoComponent } from './info/dockerinfo/dockerinfo.component';
import { ViewAllApplicationsComponent } from './dashboard/view-all-applications/view-all-applications.component';
import { StaticComponent } from './static-graph/static/static.component';
import { DisplayComponent } from './real-time/front-end1/display/display.component';
import { ChartsModule } from 'ng2-charts';
import { AdventureTimeService } from './real-time/front-end1/services/adventure-time.service';
import { SamplingComponent } from './dashboard/sampling/sampling.component';
import { DockerComponent } from './docker/docker.component';
import { NodejsComponent } from './nodejs/nodejs.component';
import { NetworkStatComponent } from './static-graph/network-stat/network-stat.component';
import { ThreadListStatComponent } from './static-graph/thread-list-stat/thread-list-stat.component';
import { HttpStatComponent } from './static-graph/http-stat/http-stat.component';
import { CpuUsageStatComponent } from './static-graph/cpu-usage-stat/cpu-usage-stat.component';
import { CpuMetricStatComponent } from './static-graph/cpu-metric-stat/cpu-metric-stat.component';
import { RamStatComponent } from './static-graph/ram-stat/ram-stat.component';
import { BarChartStatComponent } from './static-graph/bar-chart-stat/bar-chart-stat.component';
import { CpuCoresStatComponent } from './static-graph/cpu-cores-stat/cpu-cores-stat.component';
import { HealthMetricStatComponent } from './static-graph/health-metric-stat/health-metric-stat.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'auth/login',
    component: LoginComponent
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'appregister',
    component: AppregisterComponent
  },
  {
    path: 'footer',
    component: FooterComponent
  },
  {
    path: 'warinfo',
    component: WarinfoComponent
  },
  {
    path: 'jarinfo',
    component: JarinfoComponent
  },
  {
    path: 'dockerinfo',
    component: DockerinfoComponent
  },
  { path: 'YourApplications', component: ViewAllApplicationsComponent },
  { path: 'static/:userID/:appID', component: StaticComponent },
  { path: 'display/:userID/:appID', component: DisplayComponent },
  { path: 'sampling', component: SamplingComponent },
  { path: 'static1/:userID/:applicationID', component: DockerComponent },
  { path: 'static2/:userID/:applicationID', component: NodejsComponent },
  { path: 'network-stat/:date/:date1/:from/:to', component: NetworkStatComponent },
  { path: 'thread-list-stat/:date/:date1/:from/:to', component: ThreadListStatComponent },
  { path: 'http-stat/:date/:date1/:from/:to', component: HttpStatComponent },
  { path: 'cpuusage-stat/:date/:date1/:from/:to', component: CpuUsageStatComponent },
  { path: 'cputemp-stat/:date/:date1/:from/:to', component: CpuMetricStatComponent },
  { path: 'memory-stat/:date/:date1/:from/:to', component: RamStatComponent },
  { path: 'disk_utilization-stat/:date/:date1/:from/:to', component: BarChartStatComponent },
  { path: 'cpucores-stat/:date/:date1/:from/:to', component: CpuCoresStatComponent },
  { path: 'health-stat/:date/:date1/:from/:to', component: HealthMetricStatComponent }
  // {path: 'network/:date/:date1/:from/:to', component:NetworkStatComponent},
  //     {path: 'thread/:date/:date1/:from/:to', component:ThreadListStatComponent},
  //     {path: 'http/:date/:date1/:from/:to', component:HttpStatComponent},
  //     {path: 'cpuusage/:date/:date1/:from/:to', component:CpuUsageStatComponent},
  //     {path: 'cputemp/:date/:date1/:from/:to', component:CpuMetricStatComponent},
  //     {path: 'memory/:date/:date1/:from/:to', component:RamStatComponent},
  //     {path: 'disk_utilization/:date/:date1/:from/:to', component:BarChartStatComponent},
  //     {path: 'cpucores/:date/:date1/:from/:to', component:CpuCoresStatComponent},
  //     {path: 'health/:date/:date1/:from/:to', component:HealthMetricStatComponent}
];
@NgModule({
  imports: [
    RouterModule,
    RouterModule.forRoot(routes),
    ChartsModule
  ],
  declarations: [],
  exports: [RouterModule],
  providers: [AdventureTimeService]
})
export class AppRoutingModule { }
