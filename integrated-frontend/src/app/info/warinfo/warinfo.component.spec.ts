import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarinfoComponent } from './warinfo.component';

describe('WarinfoComponent', () => {
  let component: WarinfoComponent;
  let fixture: ComponentFixture<WarinfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarinfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
