import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WarNetworkComponent } from './war-network/war-network.component';
import { WarThreadComponent } from './war-thread/war-thread.component';
import { WarTempComponent } from './war-temp/war-temp.component';
import { WarUsageComponent } from './war-usage/war-usage.component';
import { WarCoresComponent } from './war-cores/war-cores.component';
import { WarMemoryComponent } from './war-memory/war-memory.component';
import { WarDisplayComponent } from './war-display/war-display.component';

@NgModule({
  imports: [
    CommonModule
  ],
  // declarations: [WarNetworkComponent, WarThreadComponent, WarTempComponent, WarUsageComponent, WarCoresComponent, WarMemoryComponent, WarDisplayComponent]
})
export class WarGraphModule { }
