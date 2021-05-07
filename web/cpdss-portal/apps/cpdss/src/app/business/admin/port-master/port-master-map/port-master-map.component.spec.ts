import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PortMasterMapComponent } from './port-master-map.component';

describe('PortMasterMapComponent', () => {
  let component: PortMasterMapComponent;
  let fixture: ComponentFixture<PortMasterMapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PortMasterMapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PortMasterMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
