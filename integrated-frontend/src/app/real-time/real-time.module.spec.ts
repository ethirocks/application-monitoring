import { RealTimeModule } from './real-time.module';

describe('RealTimeModule', () => {
  let realTimeModule: RealTimeModule;

  beforeEach(() => {
    realTimeModule = new RealTimeModule();
  });

  it('should create an instance', () => {
    expect(realTimeModule).toBeTruthy();
  });
});
