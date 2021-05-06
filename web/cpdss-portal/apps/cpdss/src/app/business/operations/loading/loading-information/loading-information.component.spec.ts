import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingInformationComponent } from './loading-information.component';

describe('LoadingInformationComponent', () => {
  let component: LoadingInformationComponent;
  let fixture: ComponentFixture<LoadingInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
