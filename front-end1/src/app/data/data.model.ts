import { DataModel } from './../health-data/data.model';
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
