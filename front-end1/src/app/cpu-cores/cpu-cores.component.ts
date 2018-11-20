import { AdventureTimeService } from './../services/adventure-time.service';
import { CpuDataModel } from '../health-data/cpu.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cpu-cores',
  templateUrl: './cpu-cores.component.html',
  styleUrls: ['./cpu-cores.component.css']
})
export class CpuCoresComponent implements OnInit {

  constructor(private atService: AdventureTimeService) { }

  container : CpuDataModel;
  res : any;

  ngOnInit() {

    this.atService.getDetails("cpuCores").subscribe((data:any) =>{ this.container = data
<<<<<<< HEAD
      this.res = this.container.results;
=======
     
      this.res = this.container.results;
     
>>>>>>> 43e94e3a0bcfdd3c4a3fc814337586a2a947fdee
    });
  }

}
