import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthMetricComponent } from './health-metric.component';

describe('HealthMetricComponent', () => {
  let component: HealthMetricComponent;
  let fixture: ComponentFixture<HealthMetricComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HealthMetricComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthMetricComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
