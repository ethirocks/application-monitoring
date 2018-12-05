export class threadValues{
   
    
    temp: number;
    time : number;
    

}
export interface bar{
    name: string,
    tags: string[],
    columns: string[],
    values: threadValues[];
  }
export interface Result{
      series: bar[],
      error: string
}
export interface CpuDataModel{
    
    results:Result[];
    error: string
}

  
export interface LineData{
  date : number;
  total : number;
}