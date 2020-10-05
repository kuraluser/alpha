import { TestBed } from '@angular/core/testing';

import { AppConfigurationService } from './app-configuration.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../../../../environments/environment';

describe('AppConfigurationService', () => {
  let service: AppConfigurationService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AppConfigurationService]
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(AppConfigurationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return catch error', () => {
    spyOn(console, 'error');
    service.load().then(() => expect(AppConfigurationService.settings).toBeUndefined())
      .catch(() => expect(AppConfigurationService.settings).toBeUndefined());
    httpTestingController.expectOne(`assets/config/config.${environment.name}.${environment.production ? 'prod' : 'dev'}.json`)
      .error(new ErrorEvent(''));

  });

  it('should return app config', () => {
    const appConfig = [
      { apiUrl: 'https://localhost' }
    ];
    service.load().then(() => expect(AppConfigurationService.settings).toEqual(appConfig));
    const req = httpTestingController.expectOne(`assets/config/config.${environment.name}.${environment.production ? 'prod' : 'dev'}.json`);
    expect(req.request.method).toBe('GET');
    req.flush(appConfig);

  });

});
