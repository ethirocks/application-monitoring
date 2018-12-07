import { Component, OnInit } from '@angular/core';
import { ApplicationServiceService } from '../../application-service.service';
import { TokenStorageService } from '../../login/auth/token-storage.service';

@Component({
  selector: 'app-sampling',
  templateUrl: './sampling.component.html',
  styleUrls: ['./sampling.component.css']
})
export class SamplingComponent implements OnInit {

  constructor(private _applicationService: ApplicationServiceService,
    private tokenstorageservice: TokenStorageService) { }
  userID=Number(this.tokenstorageservice.getUid());
  page="";
  item="sample";
  public applicationsList;
  event = { request: "", value: "",appID: 0 };
  ngOnInit() {
    this._applicationService.getApplications(this.userID).subscribe(data => this.applicationsList = data, error => this.applicationsList = "No applications available");
  }

}
