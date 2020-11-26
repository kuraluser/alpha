import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateTestingModule } from 'ngx-translate-testing';
import { DialogModule } from 'primeng/dialog';
import { VoyageApiService } from '../services/voyage-api.service';

import { NewVoyagePopupComponent } from './new-voyage-popup.component';

describe('NewVoyagePopupComponent', () => {
  let component: NewVoyagePopupComponent;
  let fixture: ComponentFixture<NewVoyagePopupComponent>;
  const ENGLISH_LANGUAGE = 'en';

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [NewVoyagePopupComponent],
      imports: [
        ReactiveFormsModule,
        DialogModule,
        RouterTestingModule,
        HttpClientTestingModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../../../assets/i18n/en.json'))],
      providers: [VoyageApiService]

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewVoyagePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
