import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CpuMetricStatComponent } from './cpu-metric-stat.component';

describe('CpuMetricComponent', () => {
  let component: CpuMetricStatComponent;
  let fixture: ComponentFixture<CpuMetricStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CpuMetricStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CpuMetricStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
