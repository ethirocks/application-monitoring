import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CpuCoresStatComponent } from './cpu-cores-stat.component';

describe('CpuCoresComponent', () => {
  let component: CpuCoresStatComponent;
  let fixture: ComponentFixture<CpuCoresStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CpuCoresStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CpuCoresStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
