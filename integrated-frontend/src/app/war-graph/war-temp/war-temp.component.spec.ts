import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarTempComponent } from './war-temp.component';

describe('WarTempComponent', () => {
  let component: WarTempComponent;
  let fixture: ComponentFixture<WarTempComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarTempComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarTempComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
