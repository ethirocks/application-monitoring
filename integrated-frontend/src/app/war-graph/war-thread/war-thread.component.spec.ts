import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarThreadComponent } from './war-thread.component';

describe('WarThreadComponent', () => {
  let component: WarThreadComponent;
  let fixture: ComponentFixture<WarThreadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarThreadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarThreadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
