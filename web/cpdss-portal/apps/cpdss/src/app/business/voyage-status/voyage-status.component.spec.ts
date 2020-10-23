import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateTestingModule } from 'ngx-translate-testing';

import { DialogModule } from 'primeng/dialog';
import { NewVoyagePopupComponent } from './new-voyage-popup/new-voyage-popup.component';

import { VoyageStatusComponent } from './voyage-status.component';

describe('VoyageStatusComponent', () => {
  let component: VoyageStatusComponent;
  let fixture: ComponentFixture<VoyageStatusComponent>;
  const ENGLISH_LANGUAGE = 'en';

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [VoyageStatusComponent, NewVoyagePopupComponent
      ],
      imports: [
        ReactiveFormsModule,
        DialogModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../../assets/i18n/en.json'))
      ]

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VoyageStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
