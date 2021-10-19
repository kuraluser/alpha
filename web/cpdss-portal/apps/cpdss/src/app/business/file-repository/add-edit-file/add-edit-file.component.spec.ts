import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditFileComponent } from './add-edit-file.component';

describe('AddEditFileComponent', () => {
  let component: AddEditFileComponent;
  let fixture: ComponentFixture<AddEditFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
