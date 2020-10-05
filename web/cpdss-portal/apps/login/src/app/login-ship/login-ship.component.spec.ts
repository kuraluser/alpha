import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginShipComponent } from './login-ship.component';
import { TranslateTestingModule } from 'ngx-translate-testing';

const ENGLISH_LANGUAGE = 'en';

describe('LoginComponent', () => {
  let component: LoginShipComponent;
  let fixture: ComponentFixture<LoginShipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginShipComponent],
      imports: [TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../assets/i18n/en.json'))
      .withDefaultLanguage(ENGLISH_LANGUAGE)]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginShipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
