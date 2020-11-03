import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { DatatableModule } from '../../../../../shared/components/datatable/datatable.module';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';

import { LoadingPortsPopupComponent } from './loading-ports-popup.component';
import { TranslateTestingModule } from 'ngx-translate-testing';
import { LoadableStudyDetailsTransformationService } from '../../../services/loadable-study-details-transformation.service';

const ENGLISH_LANGUAGE = 'en';

describe('LoadingPortsPopupComponent', () => {
  let component: LoadingPortsPopupComponent;
  let fixture: ComponentFixture<LoadingPortsPopupComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [LoadingPortsPopupComponent],
      imports: [
        DialogModule,
        ReactiveFormsModule,
        DropdownModule,
        DatatableModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../../../../../assets/i18n/en.json'))
          .withDefaultLanguage(ENGLISH_LANGUAGE)
      ],
      providers: [LoadableStudyDetailsTransformationService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingPortsPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
