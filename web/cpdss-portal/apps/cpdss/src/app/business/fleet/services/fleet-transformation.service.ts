import { Injectable } from '@angular/core';

import { IFleetVoyagePorts } from '../models/fleet-map.model';

/**
 * Transformation service for Fleet module
 *
 * @export
 * @class FleetTransformationService
 */
@Injectable({
  providedIn: 'root'
})
export class FleetTransformationService {

  constructor() { }

  /**
   * function to map icon path to port based on actual & estimated dates
   *
   * @param {IFleetVoyagePorts[]} voyagePorts
   * @return {*}  {IFleetVoyagePorts[]}
   * @memberof FleetMapComponent
   */
  mapIconsOnVoyagePort(voyagePorts: IFleetVoyagePorts[]): IFleetVoyagePorts[] {
    let portsArray = voyagePorts;
    portsArray.map((port, index) => {
      if (index === 0) {
        if (port.etd && port.ata && !port.atd) {
          port.iconUrl = 'assets/images/themes/light/map-icon/ship-location.png';
        } else if (port.eta && port.etd && !port.ata && !port.atd) {
          port.iconUrl = this.fetchIconByPortType(false, port.portType.toLowerCase());
        } else if (port.etd && port.atd && portsArray[index + 1].ata) {
          port.iconUrl = this.fetchIconByPortType(true, port.portType.toLowerCase());
        } else if (port.etd && port.atd && !portsArray[index + 1].ata && portsArray[index + 1].eta) {
          port.iconUrl = this.fetchIconByPortType(true, port.portType.toLowerCase() + '-departed');
        }
      } else if (index === portsArray.length - 1) {
        if (port.eta && !port.ata) {
          port.iconUrl = this.fetchIconByPortType(false, port.portType.toLowerCase());
        } else if (port.eta && port.ata) {
          port.iconUrl = 'assets/images/themes/light/map-icon/ship-location.png';
        }
      } else {
        if (port.ata && !port.atd) {
          port.iconUrl = 'assets/images/themes/light/map-icon/ship-location.png';
        } else if (port.ata && port.atd && !portsArray[index + 1].ata && portsArray[index + 1].eta) {
          port.iconUrl = this.fetchIconByPortType(true, port.portType.toLowerCase() + '-departed');
        } else if (port.ata && port.atd && portsArray[index + 1].ata) {
          port.iconUrl = this.fetchIconByPortType(true, port.portType.toLowerCase());
        } else if (!port.ata && !port.atd) {
          port.iconUrl = this.fetchIconByPortType(false, port.portType.toLowerCase());
        }
      }
      return port;
    });
    return portsArray;
  }

  /**
   * fucntion to map icon path based port-type with visited / not-visted
   *
   * @param {boolean} isVisited
   * @param {string} portType
   * @return {*}  {string}
   * @memberof FleetMapComponent
   */
  fetchIconByPortType(isVisited: boolean, portType: string): string {
    let iconUrl: string;
    if (isVisited) {
      switch (portType) {
        case 'loading': iconUrl = 'assets/images/themes/light/map-icon/loading.png'
          break;
        case 'loading-departed': iconUrl = 'assets/images/themes/light/map-icon/loading-departed.png'
          break;
        case 'discharging': iconUrl = 'assets/images/themes/light/map-icon/discharging.png'
          break;
        case 'discharging-departed': iconUrl = 'assets/images/themes/light/map-icon/discharging-departed.png'
          break;
        case 'bunkering': iconUrl = 'assets/images/themes/light/map-icon/bunkering.png'
          break;
        case 'bunkering-departed': iconUrl = 'assets/images/themes/light/map-icon/bunkering.png'
          break;
      }
    } else {
      switch (portType) {
        case 'loading': iconUrl = 'assets/images/themes/light/map-icon/loading-blue.png'
          break;
        case 'discharging': iconUrl = 'assets/images/themes/light/map-icon/discharging-blue.png'
          break;
        case 'bunkering': iconUrl = 'assets/images/themes/light/map-icon/bunkering-blue.png'
          break;
      }
    }
    return iconUrl;
  }

  /**
   * fucntion to show icon, indicating port-type on popup
   *
   * @param {string} portType
   * @return {*}  {string}
   * @memberof FleetMapComponent
   */
  setPortTypePopupIcon(portType: string): string {
    let iconUrl: string;
    switch (portType) {
      case 'loading': iconUrl = 'assets/images/themes/light/map-icon/ship-loading.png'
        break;
      case 'discharging': iconUrl = 'assets/images/themes/light/map-icon/ship-discharging.png'
        break;
      case 'bunkering': iconUrl = 'assets/images/themes/light/map-icon/ship-bunkering.png'
        break;
    }
    return iconUrl;
  }

}
