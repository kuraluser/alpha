import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadableQuantityComponent } from './loadable-quantity.component';

describe('LoadableQuantityComponent', () => {
  let component: LoadableQuantityComponent;
  let fixture: ComponentFixture<LoadableQuantityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadableQuantityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadableQuantityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
