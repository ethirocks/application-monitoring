import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CpuCoresComponent } from './cpu-cores.component';

describe('CpuCoresComponent', () => {
  let component: CpuCoresComponent;
  let fixture: ComponentFixture<CpuCoresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CpuCoresComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CpuCoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
