<<<<<<< HEAD
import { DataModel } from './../health-data/data.model';
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
   
  
=======
export interface DataModel {
>>>>>>> 43e94e3a0bcfdd3c4a3fc814337586a2a947fdee
    date: string;
    metric: number;
    metric1: number;
    docker: string;
<<<<<<< HEAD

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

  // export interface DataModel{
  //      letter : DM[];
  // }
=======
}
>>>>>>> 43e94e3a0bcfdd3c4a3fc814337586a2a947fdee
