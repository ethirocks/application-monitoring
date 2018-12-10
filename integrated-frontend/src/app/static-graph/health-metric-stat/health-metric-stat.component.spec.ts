import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthMetricStatComponent } from './health-metric-stat.component';

describe('HealthMetricStatComponent', () => {
  let component: HealthMetricStatComponent;
  let fixture: ComponentFixture<HealthMetricStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HealthMetricStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthMetricStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
