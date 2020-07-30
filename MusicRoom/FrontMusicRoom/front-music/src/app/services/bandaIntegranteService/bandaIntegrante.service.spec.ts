import { TestBed } from '@angular/core/testing';
import { BandaIntegranteService } from './bandaIntegrante.service';

describe('BandaIntegranteService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BandaIntegranteService = TestBed.get(BandaIntegranteService);
    expect(service).toBeTruthy();
  });
});
