import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EditPortRotationComponent } from './edit-port-rotation.component';

describe('EditPortRotationComponent', () => {
  let component: EditPortRotationComponent;
  let fixture: ComponentFixture<EditPortRotationComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EditPortRotationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPortRotationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
