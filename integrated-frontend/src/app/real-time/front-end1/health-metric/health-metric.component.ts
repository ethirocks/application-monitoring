import { AdventureTimeService } from './../services/adventure-time.service';
import { Component, OnInit } from '@angular/core';
import { HealthModel } from '../health-data/health.model';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-health-metric',
  templateUrl: './health-metric.component.html',
  styleUrls: ['./health-metric.component.css']
//   template:`
  
//   <script>
// function myFunction() {
//     var x = document.getElementById("pl").lastElementChild.text;
//     document.getElementById("demo").innerHTML = x;
// }
// </script>
//   `
})
export class HealthMetricComponent implements OnInit {

  health: HealthModel ;
  errorMsg: string;
  res : any;
  r : any;
  s : any;
  v : any;
  arr : string[];


  userID;
  appID;
  a; 
  constructor(private atService : AdventureTimeService,
    private route: ActivatedRoute) { 
      this.route.params.subscribe( params => {
        this.userID =params['userID'];
        this.appID = params['appID'];
      } );
    }
 
  ngOnInit() {

    this.atService.getDetails("health",this.userID,this.appID).subscribe((data:any) =>{ this.health = data
      // console.log("error msg " + this.errorMsg);
       console.log("ooooooooo..  "+this.health);
      console.log(this.health);
      this.res = this.health.results;
      //this.myFunction();
       //this.r = this.res.series;
      // this.s = this.r.values;
      
      //console.log("results"+this.res);
    });
  }

    onResize() {
      this.myFunction();
    }

   //   var str = this.health.results.map(d3 => d3.series.map(d3 => d3.values));
    //   var ud= str[0][0][0][3];

    // private myFunc(){
    //   this.atService.getFunc().subscribe((data:any)=> this.res = data)  ;  }
    ud:string='';
    private myFunction(){
      var str = this.health.results.map(d3 => d3.series.map(d3 => d3.values));
      this.ud= str[0][0][0][2];
      console.log("health"+ this.ud);
      return this.ud;
    }
  //   private myFunction() {
  //     var x = document.getElementById("pl").lastElementChild.textContent;
  //     document.getElementById("demo").innerHTML = x;
  // }

  
  }


