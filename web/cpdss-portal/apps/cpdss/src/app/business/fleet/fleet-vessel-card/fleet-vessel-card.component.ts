import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';

import { AppConfigurationService } from './../../../shared/services/app-configuration/app-configuration.service';
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
      vessel.voyageStart = vessel.voyagePorts.find((port, portIndex) => (portIndex === 0 && Number(port.portOrder) === 1));
      vessel.voyageEnd = vessel.voyagePorts.find((port, portIndex) => (portIndex === vessel.voyagePorts.length - 1 && Number(port.portOrder) === vessel.voyagePorts.length));
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
  dateFormat: string;
  _vesselValues: IFleetVessel[];
  userPermission: any;
  permissionDetails: any;

  constructor(
    private router: Router,
    private timeZoneTransformationService: TimeZoneTransformationService
  ) { }

  ngOnInit(): void {
    this.dateFormat = AppConfigurationService.settings.dateFormat.split(' ')[0];
    this.userPermission = JSON.parse(window.localStorage.getItem('_USER_PERMISSIONS'));
    this.setPermissionObject();
  }

  /**
   * function to set permissions
   * @memberof FleetVesselCardComponent
   */
  setPermissionObject() {
    this.permissionDetails = {
      voyageStatus: this.userPermission?.find(item => item.languageKey === AppConfigurationService.settings.permissionMapping['VoyageStatusComponent'])?.permission,
      files: this.userPermission?.find(item => item.languageKey === AppConfigurationService.settings.permissionMapping['FileRepositoryComponent'])?.permission,
      synoptical: this.userPermission?.find(item => item.languageKey === AppConfigurationService.settings.permissionMapping['SynopticalComponent'])?.permission,
      voyages: this.userPermission?.find(item => item.languageKey === AppConfigurationService.settings.permissionMapping['voyagesComponent'])?.permission,
    };
  }

  /**
   * function to re-plot map with clicked vessel card voyage ports
   *
   * @param {*} event
   * @param {IFleetVessel} vessel
   * @memberof FleetVesselCardComponent
   */
  onClickVesselCard(event, vessel: IFleetVessel) {
    this.selectedVesselId = vessel.id;
    this.selectVessel.emit({ vesselId: vessel.id, originalEvent: event });
  }

  /**
   * function to navigate from vessel card to other pages
   *
   * @param {string} key
   * @param {IFleetVessel} vessel
   * @memberof FleetVesselCardComponent
   */
  navigateToPage(key: string, vessel: IFleetVessel) {
    localStorage.setItem("vesselId", vessel.id.toString());
    switch (key) {
      case 'voyage-status':
        this.router.navigate(['/business/voyage-status']);
        break;
      case 'voyages':
        this.router.navigate(['/business/voyages']);
        break;
      case 'synoptical':
        this.router.navigate(['/business/synoptical' + '/' + vessel.id + '/' + vessel.voyageId]);
        break;
      case 'file-repo':
        this.router.navigate(['/business/file-repository']);
        break;
    }
  }

}
