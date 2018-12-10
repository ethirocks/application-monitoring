import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllApplicationsComponent } from './view-all-applications.component';

describe('ViewAllApplicationsComponent', () => {
  let component: ViewAllApplicationsComponent;
  let fixture: ComponentFixture<ViewAllApplicationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllApplicationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
