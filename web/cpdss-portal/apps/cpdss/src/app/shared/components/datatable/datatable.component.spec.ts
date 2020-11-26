import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateTestingModule } from 'ngx-translate-testing';
import { ColorPickerModule } from 'primeng/colorpicker';
import { DropdownModule } from 'primeng/dropdown';
import { TableModule } from 'primeng/table';
import { ValidationErrorModule } from '../validation-error/validation-error.module';

import { DatatableComponent } from './datatable.component';

const ENGLISH_LANGUAGE = 'en';
describe('DataTableComponent', () => {
  let component: DatatableComponent;
  let fixture: ComponentFixture<DatatableComponent>;

  beforeEach((() => {
    TestBed.configureTestingModule({
      declarations: [DatatableComponent],
      imports: [
        ReactiveFormsModule,
        TableModule,
        DropdownModule,
        ColorPickerModule,
        ValidationErrorModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../../../assets/i18n/en.json'))
          .withDefaultLanguage(ENGLISH_LANGUAGE)
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatatableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
