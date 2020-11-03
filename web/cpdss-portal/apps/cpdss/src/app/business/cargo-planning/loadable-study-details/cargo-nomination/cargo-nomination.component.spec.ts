import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { DatatableModule } from '../../../../shared/components/datatable/datatable.module';
import { MultiSelectModule } from 'primeng/multiselect';

import { CargoNominationComponent } from './cargo-nomination.component';
import { ApiTemperatureHistoryPopupComponent } from './api-temperature-history-popup/api-temperature-history-popup.component';
import { LoadingPortsPopupComponent } from './loading-ports-popup/loading-ports-popup.component';
import { DialogModule } from 'primeng/dialog';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DropdownModule } from 'primeng/dropdown';
import { TranslateTestingModule } from 'ngx-translate-testing';

const ENGLISH_LANGUAGE = 'en';
describe('CargoNominationComponent', () => {
  let component: CargoNominationComponent;
  let fixture: ComponentFixture<CargoNominationComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CargoNominationComponent, LoadingPortsPopupComponent, ApiTemperatureHistoryPopupComponent],
      imports: [
        DatatableModule,
        ReactiveFormsModule,
        MultiSelectModule,
        DialogModule,
        HttpClientTestingModule,
        DropdownModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../../../../assets/i18n/en.json'))
          .withDefaultLanguage(ENGLISH_LANGUAGE)
      ],
      providers: [LoadableStudyDetailsApiService, LoadableStudyDetailsTransformationService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoNominationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
