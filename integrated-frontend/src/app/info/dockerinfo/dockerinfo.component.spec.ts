import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DockerinfoComponent } from './dockerinfo.component';

describe('DockerinfoComponent', () => {
  let component: DockerinfoComponent;
  let fixture: ComponentFixture<DockerinfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DockerinfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DockerinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
