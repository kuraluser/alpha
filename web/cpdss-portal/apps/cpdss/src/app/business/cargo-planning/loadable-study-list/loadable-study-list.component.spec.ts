import { CommonModule } from '@angular/common';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadableStudyListComponent } from './loadable-study-list.component';

import { LoadableStudyListRoutingModule } from "./loadable-study-list-routing.module";
import { DialogModule } from 'primeng/dialog';
import { NewLoadableStudyPopupModule } from "../../core/components/new-loadable-study-popup/new-loadable-study-popup.module";
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('LoadableStudyListComponent', () => {
  let component: LoadableStudyListComponent;
  let fixture: ComponentFixture<LoadableStudyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoadableStudyListComponent],
      imports: [
        CommonModule,
        LoadableStudyListRoutingModule,
        DialogModule,
        NewLoadableStudyPopupModule
      ],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadableStudyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
