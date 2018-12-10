import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ThreadListStatComponent } from './thread-list-stat.component';

describe('ThreadListStatComponent', () => {
  let component: ThreadListStatComponent;
  let fixture: ComponentFixture<ThreadListStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ThreadListStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ThreadListStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
