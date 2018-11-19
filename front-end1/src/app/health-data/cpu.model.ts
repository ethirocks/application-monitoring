export interface threadValues{
   
  
    temp: number;
    usage: number;
    cores: number;

}
export interface bar{
    name: string,
    tags: string[],
    columns: string[],
    values: threadValues[];
  }
export interface CpuDataModel{
    results: [
      {
        series: bar[],
        error: string
      }
    ],
    error: string
  }
