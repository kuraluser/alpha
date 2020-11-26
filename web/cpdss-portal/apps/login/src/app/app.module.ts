import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppConfigurationService } from './shared/services/app-configuration/app-configuration.service';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import createTranslateLoader from './shared/services/translation/translation-http-loader.service';

// keycloak dependency
import { KeycloakService } from 'keycloak-angular';
// keycloak initializer function for shore-side login
import { keycloakShoreInitializer } from "../app/shared/utils/keycloak-shore.init";
import { CarouselModule } from 'ngx-owl-carousel-o';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: (appConfigService: AppConfigurationService) => () => appConfigService.load(),
      deps: [AppConfigurationService], multi: true
    },
    KeycloakService,
    {
      provide: APP_INITIALIZER,
      useFactory: keycloakShoreInitializer,
      deps: [KeycloakService, HttpClient, AppConfigurationService], multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
