import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDialog1Component } from './my-dialog1.component';

describe('MyDialog1Component', () => {
  let component: MyDialog1Component;
  let fixture: ComponentFixture<MyDialog1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyDialog1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDialog1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
