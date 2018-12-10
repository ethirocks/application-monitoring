import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarDisplayComponent } from './war-display.component';

describe('WarDisplayComponent', () => {
  let component: WarDisplayComponent;
  let fixture: ComponentFixture<WarDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
