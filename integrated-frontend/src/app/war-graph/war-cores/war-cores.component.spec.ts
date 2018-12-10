import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarCoresComponent } from './war-cores.component';

describe('WarCoresComponent', () => {
  let component: WarCoresComponent;
  let fixture: ComponentFixture<WarCoresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarCoresComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarCoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
