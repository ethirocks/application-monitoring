import { map } from 'rxjs/operators';
import { threadValues } from './../thread-data/thread-data.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { DataModel } from '../health-data/data.model';
import { Component, ElementRef, Input, OnChanges, ViewChild, ViewEncapsulation, HostListener, SystemJsNgModuleLoader, OnInit } from '@angular/core';
import * as d3 from 'd3';
import { timeParse } from 'd3';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
//import { BarChartComponent} from './bar-chart.component.spec';

//import { DataModel } from 'src/app/data/data.model';

@Component({
  selector: 'app-bar-chart',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss']
 //template: `<button *ngFor="let button of [1,2,3,4]; let i = index" [ngClass]="{'active':isClicked[i]}" (click)="isClicked[i] = (isClicked[i]? false :true )">up/down</button>`
})
export class BarChartComponent implements OnInit {
  @ViewChild('chart')
  private chartContainer: ElementRef;

  @Input()
  bar: DataModel;

  margin = {top: 20, right: 30, bottom: 30, left: 50};
  width = 960 - this.margin.left - this.margin.right;
  height = 600 - this.margin.top - this.margin.bottom;

  
  constructor(private atService : AdventureTimeService) { }

 // bar : DataModel[];
  errorMsg : string;
  res : any;
 

  // getCharacters(): Observable<any[]>{
  //   return Observable.of(CHARACTERS).delay(100);
  // }
  // getColumns(): string[]{
  //   return ["time", "thread_group", "thread_name", "thread_priority","thread_status","timeStamp","total_threads"]};
  // }

  ngOnInit() {
    
    
    console.log("whaaaaaatttt");
    this.atService.getDetails("disk_utilization").subscribe((data:any) =>{ this.bar = data
      console.log("error msg " + this.errorMsg);
      console.log("bars  "+this.bar);
      console.log(this.bar);
      this.res = this.bar.results;
      this.createChart();
    });
  
    // this.createChart();
  }

  onResize() {
    this.createChart();
  }


  private _url : string = "http://172.23.239.108:8080/disk_utilization";


  // getDetails(): Observable<DataModel[]>{
  //   return this.http.get<DataModel[]>(this._url);
  // }

  private createChart(): void {
    d3.select('svg').remove();

    const element = this.chartContainer.nativeElement;
    const data = this.bar;
    //this.res = this.bar.results;
    console.log("cccc"+this.bar);
    this.res = this.bar.results;
    console.log("nnnn"+this.bar);

  //   const button_list = [
  //     { text: 'item1' ,isClicked : false }
  // ]
    

  

    function parseMillisecondsIntoReadableTime(milliseconds){
      //Get hours from milliseconds
      var hours = milliseconds / (1000*60*60);
      var absoluteHours = Math.floor(hours);
      var h = absoluteHours > 9 ? absoluteHours : '0' + absoluteHours;
    
      //Get remainder from hours and convert to minutes
      var minutes = (hours - absoluteHours) * 60;
      var absoluteMinutes = Math.floor(minutes);
      var m = absoluteMinutes > 9 ? absoluteMinutes : '0' +  absoluteMinutes;
    
      //Get remainder from minutes and convert to seconds
      var seconds = (minutes - absoluteMinutes) * 60;
      var absoluteSeconds = Math.floor(seconds);
      var s = absoluteSeconds > 9 ? absoluteSeconds : '0' + absoluteSeconds;
    
    
      return m + ':' + s;
    }

    function health(info){
      var inp = info;
      var out ;
      if(inp == "up"){
        
        out = "up";
        
       
      }
      else{
        
        out = "down";
        
      }
      
      return out;
    }
    

   

    
    
    //var time = parseMillisecondsIntoReadableTime(d.date);
    
    //alert(time);

    
    const svg = d3.select(element).append('svg')
        .attr('width', this.width+this.margin.left+this.margin.right)
        .attr('height',this.height + this.margin.top+this.margin.bottom);

    const contentWidth = element.offsetWidth - this.margin.left - this.margin.right;
    const contentHeight = element.offsetHeight - this.margin.top - this.margin.bottom;

    var str = data.results.map(d3 => d3.series.map(d3 => d3.values));
    var ud= str[0][0][0][0];
     console.log("llll"+ud);
      // var obj = Object.values(str);
      // console.log("obbbbb"+obj);
    const i = 300;
    
    console.log("hhhhhh "+ i); 
    //for(var k = 0; k<i; k++){
     const x = d3
      .scaleBand()
      .rangeRound([0, this.width])
      .padding(0.1)
      .domain(data.results.map(d =>   parseMillisecondsIntoReadableTime(str[0][0][0][0])));

     //.domain(data.results.map(d => parseMillisecondsIntoReadableTime(d.series.map( p => p.values.map(u => u.date)))));
    //console.log("val "+obj);
    const y = d3
      .scaleLinear()
      .rangeRound([this.height, 0])
      .domain([0, d3.max(str, d =>  str[0][0][0][1]) ]);
    

      const y1 = d3
      .scaleLinear()
      .rangeRound([this.height, 0])
      .domain([0,d3.max(data.results,d=>str[0][0][0][2])]);

    const g = svg.append('g')
      .attr('transform', 'translate(' + this.margin.left + ',' + this.margin.top + ')');

    g.append('g')
      .attr('class', 'axis axis--x')
      .attr('transform', 'translate(0,' + this.height + ')')
      .call(d3.axisBottom(x));
      

    g.append('g')
      .attr('class', 'axis axis--y')
      .call(d3.axisLeft(y).ticks(10, ''))
      .append('text')
        .attr('transform', 'rotate(-90)')
        .attr('y', 12)
        .attr('dy', '0.71em')
        .attr('text-anchor', 'end')
        .text('Metric');

        // g.append('g')
        // .attr('class', 'axis axis--y')
        // .call(d3.axisLeft(y1).ticks(10, ''))
        // .append('text')
        //   .attr('transform', 'rotate(-90)')
        //   .attr('y1', 12)
        //   .attr('dy1', '0.71em')
        //   .attr('text-anchor', 'end')
        //   .text('Frequency');

        
    for(var k = 0; k<i; k++){
    g.selectAll('.metric')
      .data(data.results)
      .enter().append('rect')
        .attr('class', 'metric')
        .attr('x', d => x(parseMillisecondsIntoReadableTime(str[0][0][k][0])))
        .attr('y', d => y(str[0][0][k][1]))
        .attr('width', x.bandwidth())
        .attr('height', d => (this.height - y(str[0][0][k][1]) ));

        //console.log("len"+  data.results.map(p => p.series.map( o => o.values.map( l => l.metric))));
    g.selectAll('.metric1')
        .data(data.results)
        .enter().append('rect')
          .attr('class', 'metric1')
          .attr('x', d => x(parseMillisecondsIntoReadableTime(str[0][0][k][0])))
          .attr('y', d => y(str[0][0][k][1]))
          .attr('width', x.bandwidth())
          .attr('height', d => y(str[0][0][k][1]) - y(str[0][0][k][2]));// - y(d.metric1));
    }
    // g.selectAll('.docker')
    //     .data(data)
    //     .enter().append('rect')
    //        .attr('class','docker')
    //        .attr('x', d => x(parseMillisecondsIntoReadableTime(d.date)))
    //        .attr('y', d => y(health(d.docker)))
    //        .attr('width', x.bandwidth())
    //        .attr('height', d => y(d.metric1) - y(d.docker)  );
  }

}



