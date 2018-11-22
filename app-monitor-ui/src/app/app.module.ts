import { AdventureTimeService } from './services/adventure-time.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { BarChartComponent } from './bar-chart/bar-chart.component';
import { ThreadListComponent } from './thread-list/thread-list.component';
import { TableRowComponent } from './table-row/table-row.component';
import { HealthMetricComponent } from './health-metric/health-metric.component';
import { CpuMetricComponent } from './cpu-metric/cpu-metric.component';
import { CpuUsageComponent } from './cpu-usage/cpu-usage.component';
import { CpuCoresComponent } from './cpu-cores/cpu-cores.component';
import { RamComponent } from './ram/ram.component';
import { HttpComponent } from './http/http.component';
import { NetworkComponent } from './network/network.component';
import { FusionChartsModule } from 'angular-fusioncharts';

// Load FusionCharts
import * as FusionCharts from 'fusioncharts';
// Load Charts module
import * as Charts from 'fusioncharts/fusioncharts.charts';
// Load fusion theme
import * as FusionTheme from 'fusioncharts/themes/fusioncharts.theme.fusion';



FusionChartsModule.fcRoot(FusionCharts, Charts, FusionTheme)

@NgModule({
  declarations: [
    AppComponent,
    BarChartComponent,
    ThreadListComponent,
    TableRowComponent,
    HealthMetricComponent,
    CpuMetricComponent,
    CpuUsageComponent,
    CpuCoresComponent,
    RamComponent,
    HttpComponent,
    NetworkComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
  ],
  providers: [AdventureTimeService],
  bootstrap: [AppComponent]
})
export class AppModule{ }
