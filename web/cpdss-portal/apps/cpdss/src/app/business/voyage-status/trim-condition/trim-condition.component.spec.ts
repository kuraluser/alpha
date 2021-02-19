import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrimConditionComponent } from './trim-condition.component';

describe('TrimConditionComponent', () => {
  let component: TrimConditionComponent;
  let fixture: ComponentFixture<TrimConditionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrimConditionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrimConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
