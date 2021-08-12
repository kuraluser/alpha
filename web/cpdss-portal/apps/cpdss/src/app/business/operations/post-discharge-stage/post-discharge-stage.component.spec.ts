import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PostDischargeStageComponent } from './post-discharge-stage.component';

describe('PostDischargeStageComponent', () => {
  let component: PostDischargeStageComponent;
  let fixture: ComponentFixture<PostDischargeStageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostDischargeStageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostDischargeStageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
