import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDialog2Component } from './my-dialog2.component';

describe('MyDialog2Component', () => {
  let component: MyDialog2Component;
  let fixture: ComponentFixture<MyDialog2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyDialog2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDialog2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
