import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CpuUsageStatComponent } from './cpu-usage.component';

describe('CpuUsageStatComponent', () => {
  let component: CpuUsageStatComponent;
  let fixture: ComponentFixture<CpuUsageStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CpuUsageStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CpuUsageStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
