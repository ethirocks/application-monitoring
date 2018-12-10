import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HttpStatComponent } from './http-stat.component';

describe('HttpStatComponent', () => {
  let component: HttpStatComponent;
  let fixture: ComponentFixture<HttpStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HttpStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HttpStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
