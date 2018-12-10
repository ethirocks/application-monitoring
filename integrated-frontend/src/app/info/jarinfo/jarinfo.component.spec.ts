import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JarinfoComponent } from './jarinfo.component';

describe('JarinfoComponent', () => {
  let component: JarinfoComponent;
  let fixture: ComponentFixture<JarinfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JarinfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JarinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
