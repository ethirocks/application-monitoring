import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarUsageComponent } from './war-usage.component';

describe('WarUsageComponent', () => {
  let component: WarUsageComponent;
  let fixture: ComponentFixture<WarUsageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarUsageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarUsageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
