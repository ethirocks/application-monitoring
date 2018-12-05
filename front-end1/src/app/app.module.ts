import { AdventureTimeService } from './services/adventure-time.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { HealthMetricComponent } from './health-metric/health-metric.component';
import { CpuMetricComponent } from './cpu-metric/cpu-metric.component';
import { CpuCoresComponent } from './cpu-cores/cpu-cores.component';
import { RamComponent } from './ram/ram.component';
import { ChartsModule } from 'ng2-charts';



@NgModule({
  declarations: [
    AppComponent,
    HealthMetricComponent,
    CpuMetricComponent,
    CpuCoresComponent,
    RamComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ChartsModule
  ],
  providers: [AdventureTimeService],
  bootstrap: [AppComponent]
})
export class AppModule{ }
