import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateTestingModule } from 'ngx-translate-testing';
import { LoadableStudyDetailsTransformationService } from '../services/loadable-study-details-transformation.service';

import { LoadableStudyDetailsComponent } from './loadable-study-details.component';

const ENGLISH_LANGUAGE = 'en';
describe('LoadableStudyDetailsComponent', () => {
  let component: LoadableStudyDetailsComponent;
  let fixture: ComponentFixture<LoadableStudyDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoadableStudyDetailsComponent],
      imports: [RouterTestingModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../../../assets/i18n/en.json'))
        .withDefaultLanguage(ENGLISH_LANGUAGE)],
      providers: [LoadableStudyDetailsTransformationService]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadableStudyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
