import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

import { IFleetVessel, IFleetVesselCardEvent } from '../models/fleet-map.model';

@Component({
  selector: 'cpdss-portal-fleet-vessel-card',
  templateUrl: './fleet-vessel-card.component.html',
  styleUrls: ['./fleet-vessel-card.component.scss']
})
export class FleetVesselCardComponent implements OnInit {

  @Input()
  get vesselValues(): IFleetVessel[] {
    return this._vesselValues;
  }
  set vesselValues(vessels: IFleetVessel[]) {
    vessels.map(vessel => {
      vessel.voyageStart = vessel.voyageName.split('-')[0].trim();
      vessel.voyageEnd = vessel.voyageName.split('-')[1].trim();
      return vessel;
    });
    this.selectedVesselId = vessels[0].id;
    this._vesselValues = vessels;
  }
  @Output() selectVessel = new EventEmitter<IFleetVesselCardEvent>();
  selectedVesselId: number;
  _vesselValues: IFleetVessel[];

  constructor() { }

  ngOnInit(): void {
  }

  onClickVesselCard(event, vessel: IFleetVessel) {
    this.selectedVesselId = vessel.id;
    this.selectVessel.emit({vesselId: vessel.id, originalEvent: event});
  }

}
