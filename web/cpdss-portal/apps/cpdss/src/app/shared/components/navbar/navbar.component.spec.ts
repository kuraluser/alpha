import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarComponent } from './navbar.component';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateTestingModule } from 'ngx-translate-testing';
import { By } from '@angular/platform-browser';

const ENGLISH_LANGUAGE = 'en';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavbarComponent],
      imports: [
        RouterTestingModule,
        TranslateTestingModule.withTranslations(ENGLISH_LANGUAGE, require('../../../../assets/i18n/en.json'))
          .withDefaultLanguage(ENGLISH_LANGUAGE)
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('When click on a menu it shouls call submenu', () => {

    const spyValue = spyOn(component, 'showSubmenu');
    const list = [
      {
        'menu': 'OPERATIONS',
        'menuIcon': 'voyages',
        'menuLink': 'operations',
        'subMenu': [
          { 'name': 'LOADING' },
          { 'name': 'DISCHARGING' },
          { 'name': 'BUNKERING' }
        ]
      }
    ]


    component.showSubmenu(list, 3);
    expect(component.showSubmenu).toBeCalledWith(list, 3);
    expect(component.showSubmenu).toHaveBeenCalled();
    // expect(component.isSubMenu[3]).toBe(true);

  });

  it('When click on a menu it should change isSubMenu value',() => {
    const subMenu = [

          { 'name': 'LOADING' },
          { 'name': 'DISCHARGING' },
          { 'name': 'BUNKERING' }

    ];    
    component.showSubmenu(subMenu, 3);
  fixture.detectChanges();
  expect(component.isSubMenu[3]).toBe(true);
});

});
