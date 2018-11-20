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