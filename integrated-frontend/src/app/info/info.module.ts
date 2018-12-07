import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WarinfoComponent } from './warinfo/warinfo.component';
import { JarinfoComponent } from './jarinfo/jarinfo.component';
import { DockerinfoComponent } from './dockerinfo/dockerinfo.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [WarinfoComponent, JarinfoComponent, DockerinfoComponent]
})
export class InfoModule { }
