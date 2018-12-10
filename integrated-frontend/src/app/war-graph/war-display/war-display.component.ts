import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-war-display',
  templateUrl: './war-display.component.html',
  styleUrls: ['./war-display.component.css']
})
export class WarDisplayComponent implements OnInit {

  userID;
  appID;
  a;
  constructor(private route: ActivatedRoute) {
    // this.a = this.route.paramMap.pipe(
    //   switchMap(params => {
    //     this.userID = +params.get("userID");
    //     this.appID = +params.get("appID");
    //     return "";
    //   })
    // );
    this.route.params.subscribe( params => {
          this.userID =params['userID'];
          this.appID = params['appID'];
        } );
    console.log(this.userID + "...." + this.appID);
  }
  ngOnInit() {

  }
}

