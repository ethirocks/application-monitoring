import { AdventureTimeService } from './services/adventure-time.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { BarChartComponent } from './bar-chart/bar-chart.component';
import { ThreadListComponent } from './thread-list/thread-list.component';
import { TableRowComponent } from './table-row/table-row.component';


@NgModule({
  declarations: [
    AppComponent,
    BarChartComponent,
    ThreadListComponent,
    TableRowComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
  ],
  providers: [AdventureTimeService],
  bootstrap: [AppComponent]
})
export class AppModule{ }
