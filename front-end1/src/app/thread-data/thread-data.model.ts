//import { thread } from './thread-data.model';
//import { ThreadDataModel } from './thread-data.model';
// export interface ThreadDataModel{
//     // time : number;
//     // thread_group: string;
//     // thread_name : string;
//     // thread_priority: number;
//     // thread_status: string;
//     // timeStamp: string;
//     // total_Threads: string;
//     threadValues: string[];
// }
// export interface results{
//     results: series[];
// }
export interface threadValues{
    time : number;
    thread_group: string;
    thread_name : string;
    thread_priority: number;
    thread_status: string;
    timeStamp: string;
    total_Threads: string;
}
export interface thread{
    name: string,
    tags: string[],
    columns: string[],
    values: threadValues[];
  }
export interface ThreadDataModel{
    results: [
      {
        series: thread[],
        error: string
      }
    ],
    error: string
  }