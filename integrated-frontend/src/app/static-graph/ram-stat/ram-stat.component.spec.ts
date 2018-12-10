import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RamStatComponent } from './ram-stat.component';

describe('RamStatComponent', () => {
  let component: RamStatComponent;
  let fixture: ComponentFixture<RamStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RamStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RamStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
