import { map } from 'rxjs/operators';
import { threadValues } from './../thread-data/thread-data.model';
import { AdventureTimeService } from './../services/adventure-time.service';
import { DataModel } from '../health-data/data.model';
import { Component, ElementRef, Input, OnChanges, ViewChild, ViewEncapsulation, HostListener, SystemJsNgModuleLoader, OnInit } from '@angular/core';
import * as d3 from 'd3';
import { timeParse } from 'd3';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-bar-chart',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss']

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

  errorMsg : string;
  res : any;
 

  ngOnInit() {
    
    
    this.atService.getDetails("disk_utilization").subscribe((data:any) =>{ this.bar = data
      this.res = this.bar.results;
      this.createChart();
    });
  
    
  }

  onResize() {
    this.createChart();
  }


  private _url : string = "http://172.23.239.108:8080/disk_utilization";



  private createChart(): void {
    d3.select('svg').remove();

    const element = this.chartContainer.nativeElement;
    const data = this.bar;
  
    this.res = this.bar.results;

 
    

  

    function parseMillisecondsIntoReadableTime(milliseconds){
      var hours = milliseconds / (1000*60*60);
      var absoluteHours = Math.floor(hours);
      var h = absoluteHours > 9 ? absoluteHours : '0' + absoluteHours;
    
      var minutes = (hours - absoluteHours) * 60;
      var absoluteMinutes = Math.floor(minutes);
      var m = absoluteMinutes > 9 ? absoluteMinutes : '0' +  absoluteMinutes;
    

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
    

   

    
    

    
    const svg = d3.select(element).append('svg')
        .attr('width', this.width+this.margin.left+this.margin.right)
        .attr('height',this.height + this.margin.top+this.margin.bottom);

    const contentWidth = element.offsetWidth - this.margin.left - this.margin.right;
    const contentHeight = element.offsetHeight - this.margin.top - this.margin.bottom;

    var str = data.results.map(d3 => d3.series.map(d3 => d3.values));
    var ud= str[0][0][0][0];
    const i = 300;
    
     const x = d3
      .scaleBand()
      .rangeRound([0, this.width])
      .padding(0.1)
      .domain(data.results.map(d =>   parseMillisecondsIntoReadableTime(str[0][0][0][0])));

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


        
    for(var k = 0; k<i; k++){
    g.selectAll('.metric')
      .data(data.results)
      .enter().append('rect')
        .attr('class', 'metric')
        .attr('x', d => x(parseMillisecondsIntoReadableTime(str[0][0][k][0])))
        .attr('y', d => y(str[0][0][k][1]))
        .attr('width', x.bandwidth())
        .attr('height', d => (this.height - y(str[0][0][k][1]) ));

    g.selectAll('.metric1')
        .data(data.results)
        .enter().append('rect')
          .attr('class', 'metric1')
          .attr('x', d => x(parseMillisecondsIntoReadableTime(str[0][0][k][0])))
          .attr('y', d => y(str[0][0][k][1]))
          .attr('width', x.bandwidth())
          .attr('height', d => y(str[0][0][k][1]) - y(str[0][0][k][2]));
    }
  }

}



