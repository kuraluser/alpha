import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ServiceWorkerService } from './shared/services/service-worker/service-worker.service';

@Component({
  selector: 'cpdss-portal-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {


  constructor(private translateService: TranslateService, private serviceWorkerService: ServiceWorkerService) {
    this.initLanguageTranslator();
    this.serviceWorkerService.checkForUpdate();
    this.setFavicon();
  }

  /**
   * Initialize translation service
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

  /**
   * changes favicon dynamically
   */
  setFavicon(){
    const favicon = localStorage.getItem('favicon');
    if (favicon) {
    const dynamicFavicon = document.getElementById('cpdssfavicon');
    dynamicFavicon.setAttribute('href', favicon);
    }
  }
}
