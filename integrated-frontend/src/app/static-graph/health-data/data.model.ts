import { Time, DatePipe } from "@angular/common";
import { Timestamp } from "rxjs/internal/operators/timestamp";
import { Variable } from "@angular/compiler/src/render3/r3_ast";

// export interface DataModel {
//     date: string;
//     metric: number;
//     metric1: number;
//     docker: string;
// }

export interface threadValues{
   
  
    date: string;
    metric: number;
    metric1: number;
    docker: string;

}
export interface bar{
    name: string,
    tags: string[],
    columns: string[],
    values: threadValues[];
  }
export interface DataModel{
    results: [
      {
        series: bar[],
        error: string
      }
    ],
    error: string
  }


  export interface LineData{
    date : number;
    total : number;
  }