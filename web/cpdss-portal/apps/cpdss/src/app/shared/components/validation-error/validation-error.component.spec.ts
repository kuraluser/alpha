import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { TranslateTestingModule } from 'ngx-translate-testing';
import { TooltipModule } from 'primeng/tooltip';

import { ValidationErrorComponent } from './validation-error.component';

const ENGLISH_LANGUAGE = 'en';

describe('ValidationErrorComponent', () => {
  let component: ValidationErrorComponent;
  let fixture: ComponentFixture<ValidationErrorComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ValidationErrorComponent],
      imports: [TooltipModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../../../assets/i18n/en.json'))
          .withDefaultLanguage(ENGLISH_LANGUAGE)]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ValidationErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
