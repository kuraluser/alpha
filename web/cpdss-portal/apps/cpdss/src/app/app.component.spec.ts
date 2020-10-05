import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AppComponent } from './app.component';
import { TranslateTestingModule } from 'ngx-translate-testing';
import { TranslateService } from '@ngx-translate/core';

const ENGLISH_LANGUAGE = 'en';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../assets/i18n/en.json'))
          .withDefaultLanguage(ENGLISH_LANGUAGE)
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should initialise default as en when no language is set from localstorage', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const translateService = TestBed.inject(TranslateService);
    app.initLanguageTranslator();
    expect(translateService.getDefaultLang()).toEqual(ENGLISH_LANGUAGE);
  });

  it('should initialise to corresponding language if set inside localstorage', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    localStorage.setItem('language', ENGLISH_LANGUAGE);
    const translateService = TestBed.inject(TranslateService);
    app.initLanguageTranslator();
    expect(translateService.getDefaultLang()).toEqual(ENGLISH_LANGUAGE);
  });

});
