import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';

import { IFleetVessel, IFleetVesselCardEvent } from '../models/fleet-map.model';
import { IDateTimeFormatOptions } from '../../../shared/models/common.model';

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
    const formatOptions: IDateTimeFormatOptions = { stringToDate: true };
    vessels.map(vessel => {
      vessel.voyageStart = vessel.voyageName && vessel.voyageName.split('-')[0].trim();
      vessel.voyageEnd = vessel.voyageName && vessel.voyageName.split('-')[1].trim();
      if (vessel.ata) { vessel.ata = this.timeZoneTransformationService.formatDateTime(vessel?.ata, formatOptions); }
      if (vessel.atd) { vessel.atd = this.timeZoneTransformationService.formatDateTime(vessel?.atd, formatOptions); }
      if (vessel.eta) { vessel.eta = this.timeZoneTransformationService.formatDateTime(vessel?.eta, formatOptions); }
      return vessel;
    });
    this.selectedVesselId = vessels[0].id;
    this._vesselValues = vessels;
  }

  @Output() selectVessel = new EventEmitter<IFleetVesselCardEvent>();

  selectedVesselId: number;
  _vesselValues: IFleetVessel[];

  constructor(private timeZoneTransformationService: TimeZoneTransformationService) { }

  ngOnInit(): void {
  }

  onClickVesselCard(event, vessel: IFleetVessel) {
    this.selectedVesselId = vessel.id;
    this.selectVessel.emit({ vesselId: vessel.id, originalEvent: event });
  }

}
