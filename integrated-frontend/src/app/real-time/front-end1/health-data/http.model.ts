export interface threadValues{
   
  
    // date: string;
    // metric: number;
    // metric1: number;
    // docker: string;

    requestMethod : string;
    requestUrl :string;
    sessionId :string;
    sessionCreationTime : number;
    sessionLastAccessedTime : number;
    responseTime : number;
    serverName : string;
    serverPort : number;
    responseStatus : number;
    requestId : number;
    requestCount : number;

}
export interface bar{
    name: string,
    tags: string[],
    columns: string[],
    values: threadValues[];
  }
export interface HttpModel{
    results: [
      {
        series: bar[],
        error: string
      }
    ],
    error: string
  }
