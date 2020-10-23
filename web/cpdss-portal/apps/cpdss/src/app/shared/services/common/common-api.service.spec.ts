import { HttpClient, HttpClientModule, HttpHandler, HttpHeaders } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { CommonApiService } from './common-api.service';

describe('CommonService', () => {
  let service: CommonApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        HttpClientTestingModule,
        HttpClient,
        HttpHandler
      ]
    });
    service = TestBed.inject(CommonApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
