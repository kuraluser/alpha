import { async, ComponentFixture, fakeAsync, TestBed, tick, waitForAsync } from '@angular/core/testing';

import { NewLoadableStudyPopupComponent } from './new-loadable-study-popup.component';
import { NewLoadableStudyPopupModule } from "./new-loadable-study-popup.module";
import { CommonApiService } from "../../../../shared/services/common/common-api.service";
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { CommonModule } from '@angular/common';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('NewLoadableStudyPopupComponent', () => {
  let component: NewLoadableStudyPopupComponent;
  let fixture: ComponentFixture<NewLoadableStudyPopupComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [NewLoadableStudyPopupComponent],
      imports: [
        CommonModule,
        ReactiveFormsModule,
        DropdownModule,
        FileUploadModule
      ],
      providers: [
        CommonApiService
      ],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewLoadableStudyPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
