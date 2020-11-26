import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { ThemeService } from './shared/services/theme-service/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  isToggle: boolean = true;
  darkMode$: Observable<boolean>;

  constructor(private translateService: TranslateService, private themeService: ThemeService) {
    this.initLanguageTranslator();
  }

   /**
   * Change theme on button click
   */
  setDarkMode() {
    this.isToggle = !this.isToggle;
    this.themeService.setDarkMode(this.isToggle);
  }



  /**
   * INITIALISE TRANSLATION SERVICE
   */
  initLanguageTranslator(): void {
    const language = localStorage.getItem('language');
    if (language) {
      this.translateService.setDefaultLang(language);
      this.translateService.use(language);
    } else {
      this.translateService.setDefaultLang('en');
      this.translateService.use('en');
    }
  }
}
