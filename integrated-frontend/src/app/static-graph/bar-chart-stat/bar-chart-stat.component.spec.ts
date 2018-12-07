import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BarChartStatComponent } from './bar-chart-stat.component';

describe('BarChartComponent', () => {
  let component: BarChartStatComponent;
  let fixture: ComponentFixture<BarChartStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BarChartStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BarChartStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
