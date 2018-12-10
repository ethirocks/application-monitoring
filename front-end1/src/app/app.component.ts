import { Component, OnInit, ElementRef } from '@angular/core';
import { Observable } from 'rxjs';
import { DataModel } from './health-data/data.model';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ang-d3';
  data: Observable<DataModel>;

  constructor(private elementRef: ElementRef){

  }

  ngAfterViewInit(){
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor = 'white';
 }

  // constructor(private http : HttpClient){
  //   this.data = this.http.get<DataModel>('./assets/data.json');
    
  // }
  // docker : object[];

  // private _url : string = "http://172.23.239.108:8080/api/v1/metrics/thread";
  // public getJSON(): Observable<any> {
  //   return this.http.get<DataModel>('./assets/data.json')
  //                   .map((res:any) => res.json)
  //                   .catch((error:any) => console.log(error));
  // }

  // ngOnInit(){
  //   this.http.get<any>('./this._url').subscribe(
  //     data1 => {

  //     }
  //   )
  // }
//   ngOnInit() {
//     // this.http.get<DataModel>('./assets/data.json').subscribe(
//     // data1 => {
//     //   let res = data1[0];
//     //   this.docker = res['docker'];
      
//     //   console.log(this.docker);
//     // });

    
//   }
// }
}
