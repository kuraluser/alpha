import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StoreModule } from '@ngrx/store';
import { reducers, metaReducers } from './shared/store/reducers';
import { EffectsModule } from '@ngrx/effects';
import { AppEffects } from './shared/store/effects/app.effects';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import createTranslateLoader from './shared/services/translation/translation-http-loader.service';
import { AppConfigurationService } from './shared/services/app-configuration/app-configuration.service';
import { ActivatedRoute } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { keycloakCPDSSInitializer } from "../app/shared/utils/keycloak.cpdss.init";
import { HttpAuthInterceptor } from "../app/shared/services/interceptors/auth.interceptors";
import { NgxSpinnerModule } from 'ngx-spinner';
import { ToastAlertModule } from './shared/components/toast-alert/toast-alert.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    AccessDeniedComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    StoreModule.forRoot(reducers, {
      metaReducers
    }),
    EffectsModule.forRoot([AppEffects]),
    ServiceWorkerModule.register(environment.serviceWorkerScript),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    }),
    NgxSpinnerModule,
    ToastAlertModule.forRoot()
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: (appConfigService: AppConfigurationService) => () => appConfigService.load(),
      deps: [AppConfigurationService, ActivatedRoute], multi: true
    },
    KeycloakService,
    { provide: APP_INITIALIZER, useFactory: keycloakCPDSSInitializer, multi: true, deps: [KeycloakService, HttpClient, AppConfigurationService, ActivatedRoute] },
    { provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
