// import { results } from './../real-time/front-end1/thread-data/thread-data.model';
// import { DockerMetrics } from './../';
import { ActivatedRoute } from '@angular/router';
import { DockerService } from './../docker.service';
import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-docker',
  templateUrl: './docker.component.html',
  styleUrls: ['./docker.component.css']
})
export class DockerComponent implements OnInit {
userID;
applicationID;
public res:any;
  constructor(private _dockermetrics : DockerService,private route:ActivatedRoute) { 
    this.route.params.subscribe( params => {
      this.userID =params['userID'];
      this.applicationID = params['applicationID'];
      
    } );

   
  }
  // container:DockerMetrics;
  
  ngOnInit() {
    this.getdata();
  }

    // getdata(){
    //   console.log(this.applicationID);
    //   this._dockermetrics.getDetails("Container",this.userID,this.applicationID).subscribe(function (data1) { 
    //     //console.log("ud"+ a);
    //     console.log(data1);
    //     this.data = data1
    //     //  console.log(JSON.stringify(dat))
    //     //console.log(data1.results.length)
    //     this.res = data1.results;
    //     console.log("reeeeee",this.res);
    //     // data1.results.map(function(results) {
    //     //     results.series.map(function(e) {
    //     //      console.log("e",e.values)
    //     //       e.values.map(function(eachValueArray) {
    //     //         //  this.res = eachValueArray
    //     //       // console.log("values",this.res )
    //     //         })
    //     //       })
              
    //     //     })
    //         //console.log("temp"+result.series);
    //     })
    // }
    res1:any;
    getdata(){
      this._dockermetrics.getDetails("Container",this.userID,this.applicationID).subscribe((data:any)=>{
        this.res=data
        this.res1=this.res.results
      })
            // data1.results.map(function(results) {
            //     results.series.map(function(e) {
            //      console.log("e",e.values)
            //       e.values.map(function(eachValueArray) {
            //         //  this.res = eachValueArray
            //       // console.log("values",this.res )
            //         })
            //       })
                  
            //     })
                //console.log("temp"+result.series);
            
    }
}
