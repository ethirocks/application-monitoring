import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CpuMetricComponent } from './cpu-metric.component';

describe('CpuMetricComponent', () => {
  let component: CpuMetricComponent;
  let fixture: ComponentFixture<CpuMetricComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CpuMetricComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CpuMetricComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
