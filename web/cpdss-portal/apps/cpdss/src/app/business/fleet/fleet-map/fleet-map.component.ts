import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

import 'ol/ol.css';
import { Feature, Map, Overlay, View } from 'ol';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import VectorLayer from 'ol/layer/Vector';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import Style from 'ol/style/Style';
import StyleIcon from 'ol/style/Icon';
import StyleText from 'ol/style/Text';
import VectorSource from 'ol/source/Vector';
import Fill from 'ol/style/Fill';
import Stroke from 'ol/style/Stroke';
import * as _ from 'lodash';

import { FleetApiService } from '../services/fleet-api.service';
import { FleetTransformationService } from '../services/fleet-transformation.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';

import { IFleetNotificationResponse, IFleetVessel, IFleetVesselResponse, IFleetVoyagePorts } from './../models/fleet-map.model';
import { IDateTimeFormatOptions } from '../../../shared/models/common.model';

/**
 * Component to integrate fleet
 *
 * @export
 * @class FleetMapComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-fleet-map',
  templateUrl: './fleet-map.component.html',
  styleUrls: ['./fleet-map.component.scss']
})
export class FleetMapComponent implements OnInit {

  @ViewChild('portPopup', { static: false }) portPopup: ElementRef;

  map: Map;
  view: View;
  tileLayer: TileLayer;
  vectorLayor: VectorLayer;
  popupOverlay: Overlay;
  minZoom: number = 0;
  maxZoom: number = 20;

  showALlVessel: boolean = false;
  vessels: IFleetVessel[];
  selectedVessel: IFleetVessel;
  hidePortPopUp = true;
  voyagePorts: IFleetVoyagePorts[];
  cardValues: IFleetVoyagePorts;
  vesselNotifications: IFleetNotificationResponse;

  constructor(
    private fleetApiService: FleetApiService,
    private fleetTansformationService: FleetTransformationService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private renderer: Renderer2,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.initLoadVesselCards();
  }

  /**
   * function to load vessels
   *
   * @memberof FleetMapComponent
   */
  async initLoadVesselCards() {
    this.ngxSpinnerService.show();
    const vesselDetailsResponse: IFleetVesselResponse = await this.fleetApiService.getVesselDaetails().toPromise();
    if (vesselDetailsResponse.responseStatus.status === '200') {
      this.vessels = vesselDetailsResponse.shoreList.length ? vesselDetailsResponse.shoreList : null;
      if (this.vessels) {
        this.vessels.forEach(vessel => {
          return vessel.voyagePorts && this.fleetTansformationService.mapIconsOnVoyagePort(vessel.voyagePorts);
        });
        await this.initMap(this.vessels[0]?.id);
        await this.initPortPopupOnMap();
      }
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * function to initialise map
   *
   * @memberof FleetMapComponent
   */
  initMap(vesselId: number): void {
    this.ngxSpinnerService.show();
    const formatOptions: IDateTimeFormatOptions = { stringToDate: true };
    this.selectedVessel = this.vessels.find(vessel => (vessel.id === vesselId));
    this.voyagePorts = [...this.selectedVessel.voyagePorts].map((port, index) => {
      port.vesselName = this.selectedVessel.vesselName;
      port.portTypeIconUrl = this.fleetTansformationService.setPortTypePopupIcon(port.portType.toLowerCase());
      port.eta = (port.eta !== '' && port.eta !== null) ? this.timeZoneTransformationService.formatDateTime(port?.eta, formatOptions) : null;
      port.etd = (port.etd !== '' && port.etd !== null) ? this.timeZoneTransformationService.formatDateTime(port?.etd, formatOptions) : null;
      port.ata = (port.ata !== '' && port.ata !== null) ? this.timeZoneTransformationService.formatDateTime(port?.ata, formatOptions) : null;
      port.atd = (port.atd !== '' && port.atd !== null) ? this.timeZoneTransformationService.formatDateTime(port?.atd, formatOptions) : null;
      return port;
    });

    document.getElementById("fleetMap").innerHTML = "";
    this.view = new View({
      center: [9441372.9389, 2583371.7609],
      zoom: this.minZoom,
      minZoom: this.minZoom,
      maxZoom: this.maxZoom
    });

    this.tileLayer = new TileLayer({
      source: new OSM({
        url: 'https://2.aerial.maps.cit.api.here.com/maptile/2.1/maptile/newest/satellite.day/{z}/{x}/{y}/256/png?app_id=cLgX3ixGuYhf4ThwarGi&app_code=wdB8U0mlVXcHSQEGBmkf5A',
        crossOrigin: 'Anonymous'
      })
    });

    const vectorSource = this.setGeoMapPorts(this.voyagePorts);
    this.vectorLayor = new VectorLayer({
      source: vectorSource
    });

    this.map = new Map({
      target: 'fleetMap',
      layers: [this.tileLayer, this.vectorLayor],
      view: this.view,
      pixelRatio: 1,
    });

    this.ngxSpinnerService.hide();
  }

  /**
   * function to plot port locations on map
   *
   * @param {IFleetVoyagePorts[]} portlist
   * @return {*}  {*}
   * @memberof FleetMapComponent
   */
  setGeoMapPorts(portlist: IFleetVoyagePorts[]): any {
    let feature = [];
    for (const _port of portlist) {
      const port = new Feature({
        geometry: new Point(fromLonLat([Number(_port.lat), Number(_port.lon)])),
        objectType: 'PortPositionMarker',
        name: _port
      });

      const portStyleIcon = new Style({
        image: new StyleIcon({
          crossOrigin: 'anonymous',
          src: _port.iconUrl,
          anchor: [0.45, 0.5],
          scale: 1.5
        }),
        text: new StyleText({
          text: _port.portOrder + '. ' + _port.portName,
          font: 'normal 14px Play',
          textAlign: 'left',
          textBaseline: 'middle',
          fill: new Fill({
            color: 'white',
          }),
          stroke: new Stroke({
            color: 'black',
            width: 3
          }),
          offsetX: 15,
          offsetY: -15
        })
      });
      port.setStyle(portStyleIcon);
      feature.push(port)
    }

    const vectorSource = new VectorSource({
      features: feature
    });

    return vectorSource;
  }

  /**
   * funtion to map port-info and show popup
   *
   * @memberof FleetMapComponent
   */
  initPortPopupOnMap(): void {
    this.popupOverlay = new Overlay({
      element: this.portPopup.nativeElement,
    });
    this.map.addOverlay(this.popupOverlay);

    this.map.on('pointermove', (ev) => {
      const pixel = this.map.getEventPixel(ev.originalEvent);
      const hit = this.map.hasFeatureAtPixel(pixel);
      document.getElementById('fleetMap').style.cursor = hit ? 'pointer' : '';
    });
    this.map.on('pointermove', (event) => {
      this.hidePortPopUp = true;
      const feature = event.map.forEachFeatureAtPixel(event.pixel, (feature, layer) => (feature));
      if (!_.isNull(feature) && (feature !== undefined)) {

        const objectTypeName = !_.isNil(feature.get('objectType')) ? feature.get('objectType') : null;
        const portData = !_.isNil(feature.get('name')) ? feature.get('name') : null;

        if (objectTypeName === 'PortPositionMarker') {
          this.hidePortPopUp = false;
          this.cardValues = portData;
          setTimeout(() => {
            const coordinate = event.coordinate;
            this.popupOverlay.setPosition(coordinate);

            // setposition not working , so going ahead with this customisation
            const el = this.portPopup.nativeElement;
            if (!_.isNull(el) && !_.isNil(event.pixel)) {
              this.renderer.setStyle(el, 'left', event.pixel[0]);
              this.renderer.setStyle(el, 'top', event.pixel[1]);
            }
          }, 100);
        }
      }
    });
  }

  /**
   * function to plot vessel-voyage-ports to map on select vessel card
   *
   * @param {*} event
   * @memberof FleetMapComponent
   */
  onSelectVessel(event): void {
    const vesselId = event.vesselId;
    this.initMap(vesselId);
    this.initPortPopupOnMap();
  }

  /**
   * function to expnad and collapse vessel card view
   *
   * @memberof FleetMapComponent
   */
  toggleViewShowAllVessel(): void {
    this.showALlVessel = !this.showALlVessel;
  }

}
