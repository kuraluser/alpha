import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessComponent } from './business.component';
import { NavbarModule } from '../shared/components/navbar/navbar.module';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateTestingModule } from 'ngx-translate-testing';

const ENGLISH_LANGUAGE = 'en';

describe('BusinessComponent', () => {
  let component: BusinessComponent;
  let fixture: ComponentFixture<BusinessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BusinessComponent],
      imports: [
        RouterTestingModule,
        NavbarModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../assets/i18n/en.json'))
          .withDefaultLanguage(ENGLISH_LANGUAGE)
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
