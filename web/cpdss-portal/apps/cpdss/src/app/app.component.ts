import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'cpdss-portal-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(private translateService: TranslateService) {
    this.initLanguageTranslator();
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
