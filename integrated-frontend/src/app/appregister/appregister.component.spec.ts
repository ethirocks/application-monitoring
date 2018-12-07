import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppregisterComponent } from './appregister.component';

describe('AppregisterComponent', () => {
  let component: AppregisterComponent;
  let fixture: ComponentFixture<AppregisterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppregisterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppregisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
