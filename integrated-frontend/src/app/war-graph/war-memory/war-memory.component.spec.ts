import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarMemoryComponent } from './war-memory.component';

describe('WarMemoryComponent', () => {
  let component: WarMemoryComponent;
  let fixture: ComponentFixture<WarMemoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarMemoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarMemoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
