import { threadValues } from './../thread-data/thread-data.model';
export interface threadValues {
    time : number;
    details_diskFree : number;
    details_diskTotal : number;
    status : string[];
}
export interface thread{
    name: string,
    tags: string[],
    columns: string[],
    values: threadValues[];
  }
export interface HealthModel{
    results: [
      {
        series: thread[],
        error: string
      }
    ],
    error: string
  }