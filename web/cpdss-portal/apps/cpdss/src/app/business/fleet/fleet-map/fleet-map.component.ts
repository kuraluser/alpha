import { AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import 'ol/ol.css';
import { Feature, Map, Overlay, View } from 'ol';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import VectorLayer from 'ol/layer/Vector';
import XYZ from 'ol/source/XYZ';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import Style from 'ol/style/Style';
import StyleIcon from 'ol/style/Icon';
import StyleText from 'ol/style/Text';
import VectorSource from 'ol/source/Vector';
import Fill from 'ol/style/Fill';
import Stroke from 'ol/style/Stroke';
import * as _ from 'lodash';

import { NgxSpinnerService } from 'ngx-spinner';

import { IFleetVessel, IFleetVoyagePorts } from './../models/fleet-map.model';

/**
 * this dummy data will remove once actual data given
 */
const vesselInfo = {
  vessels: [
    {
      id: 149,
      voyageName: 'Mina Al Ahmadi - Shimotsu',
      vesselName: 'KAZUSA',
      flagImage: '../../../../assets/images/flags/japan.png',
      atd: '20/03/2021',
      eta: '26/03/2021',
      imoNo: 9513402,
      voyagePorts: [{
        portname: 'MINA AL AHMADI',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-2824108-2344070.png',
        atd: '20/03',
        lat: 48.163475,
        lon: 29.066295
      }, {
        portname: 'ZIRKU ISLAND',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-2824108-2344070.png',
        atd: '21/03',
        lat: 52.9897,
        lon: 25.0203
      }, {
        portname: 'KOCHIN',
        portType: 'loading',
        anchorage: true,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/port-location-1960148-1654998.png',
        atd: '22/03',
        lat: 76.2678,
        lon: 9.9546
      }, {
        portname: 'KIIRE',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        lat: 130.55,
        lon: 31.38753
      }, {
        portname: 'SHIMOTSU',
        portType: 'discharging',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        lat: 135.13335,
        lon: 34.117275
      }]
    }, {
      id: 150,
      voyageName: 'Shimotsu - Mina Ahmadi',
      vesselName: 'HAKUSAN',
      flagImage: '../../../../assets/images/flags/japan.png',
      atd: '20/03/2021',
      eta: '26/03/2021',
      imoNo: 9535058,
      voyagePorts: [{
        portname: 'MINA AL AHMADI',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        atd: '20/03',
        lat: 48.163475,
        lon: 29.066295
      }, {
        portname: 'ZIRKU ISLAND',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        atd: '21/03',
        lat: 52.9897,
        lon: 25.0203
      }, {
        portname: 'KOCHIN',
        portType: 'loading',
        anchorage: true,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/port-location-1960148-1654998.png',
        atd: '22/03',
        lat: 76.2678,
        lon: 9.9546
      }, {
        portname: 'KIIRE',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-2824108-2344070.png',
        lat: 130.55,
        lon: 31.38753
      }, {
        portname: 'SHIMOTSU',
        portType: 'discharging',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-2824108-2344070.png',
        lat: 135.13335,
        lon: 34.117275
      }]
    }, {
      id: 151,
      voyageName: 'Zirku Island - Shimotsu',
      vesselName: 'KIRISHIMA',
      flagImage: '../../../../assets/images/flags/japan.png',
      atd: '20/03/2021',
      eta: '26/03/2021',
      imoNo: 9513402,
      voyagePorts: [{
        portname: 'ZIRKU ISLAND',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-2824108-2344070.png',
        atd: '21/03',
        lat: 52.9897,
        lon: 25.0203
      }, {
        portname: 'KOCHIN',
        portType: 'loading',
        anchorage: true,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/port-location-1960148-1654998.png',
        atd: '22/03',
        lat: 76.2678,
        lon: 9.9546
      }, {
        portname: 'KIIRE',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        lat: 130.55,
        lon: 31.38753
      }, {
        portname: 'SHIMOTSU',
        portType: 'discharging',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        lat: 135.13335,
        lon: 34.117275
      }]
    }, {
      id: 152,
      voyageName: 'Mina Al Ahmadi - Shimotsu',
      vesselName: 'KAZUSA',
      flagImage: '../../../../assets/images/flags/japan.png',
      atd: '20/03/2021',
      eta: '26/03/2021',
      imoNo: 9513402,
      voyagePorts: [{
        portname: 'MINA AL AHMADI',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-2824108-2344070.png',
        atd: '20/03',
        lat: 48.163475,
        lon: 29.066295
      }, {
        portname: 'ZIRKU ISLAND',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-2824108-2344070.png',
        atd: '21/03',
        lat: 52.9897,
        lon: 25.0203
      }, {
        portname: 'KOCHIN',
        portType: 'loading',
        anchorage: true,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/port-location-1960148-1654998.png',
        atd: '22/03',
        lat: 76.2678,
        lon: 9.9546
      }, {
        portname: 'KIIRE',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        lat: 130.55,
        lon: 31.38753
      }, {
        portname: 'SHIMOTSU',
        portType: 'discharging',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        lat: 135.13335,
        lon: 34.117275
      }]
    }, {
      id: 153,
      voyageName: 'Mina Al Ahmadi - Shimotsu',
      vesselName: 'KAZUSA',
      flagImage: '../../../../assets/images/flags/japan.png',
      atd: '20/03/2021',
      eta: '26/03/2021',
      imoNo: 9513402,
      voyagePorts: [{
        portname: 'MINA AL AHMADI',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-2824108-2344070.png',
        atd: '20/03',
        lat: 48.163475,
        lon: 29.066295
      }, {
        portname: 'ZIRKU ISLAND',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-2824108-2344070.png',
        atd: '21/03',
        lat: 52.9897,
        lon: 25.0203
      }, {
        portname: 'KOCHIN',
        portType: 'loading',
        anchorage: true,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/port-location-1960148-1654998.png',
        atd: '22/03',
        lat: 76.2678,
        lon: 9.9546
      }, {
        portname: 'KIIRE',
        portType: 'loading',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        lat: 130.55,
        lon: 31.38753
      }, {
        portname: 'SHIMOTSU',
        portType: 'discharging',
        anchorage: false,
        iconUrl: 'https://cdn.iconscout.com/icon/premium/png-256-thumb/location-pin-2120117-1785458.png',
        lat: 135.13335,
        lon: 34.117275
      }]
    }
  ]
};

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
export class FleetMapComponent implements OnInit, AfterViewInit {

  @ViewChild('portPopup', { static: false }) portPopup: ElementRef;

  map: Map;
  view: View;
  tileLayer: TileLayer;
  vectorLayor: VectorLayer;
  popupOverlay: Overlay;
  minZoom: number = 0;
  maxZoom: number = 20;

  vessels: IFleetVessel[];
  selectedVessel: IFleetVessel;
  voyagePorts: IFleetVoyagePorts[];
  cardValues: IFleetVoyagePorts;
  hidePortPopUp = true;

  constructor(
    private renderer: Renderer2,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.initLoadVesselCards();
    this.initMap(this.vessels[0]?.id);
  }

  ngAfterViewInit(): void {
    this.initPortPopupOnMap();
  }

  /**
   * function to load vessels
   *
   * @memberof FleetMapComponent
   */
  initLoadVesselCards(): void {
    this.vessels = vesselInfo.vessels;
  }

  /**
   * function to initialise map
   *
   * @memberof FleetMapComponent
   */
  initMap(vesselId: number): void {
    this.selectedVessel = this.vessels.find(vessel => (vessel.id === vesselId));
    this.voyagePorts = [...this.selectedVessel.voyagePorts].map((port, i) => {
      port.vesselName = this.selectedVessel.vesselName;
      port.prevPort = this.selectedVessel.voyagePorts[i - 1] ? this.selectedVessel.voyagePorts[i - 1].portname : '-';
      port.nextPort = this.selectedVessel.voyagePorts[i + 1] ? this.selectedVessel.voyagePorts[i + 1].portname : '-';
      return port;
    });
    this.ngxSpinnerService.show();

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
        geometry: new Point(fromLonLat([_port.lat, _port.lon])),
        objectType: 'PortPositionMarker',
        name: _port
      });

      const portStyleIcon = new Style({
        image: new StyleIcon({
          crossOrigin: 'anonymous',
          src: _port.iconUrl,
          anchor: [0.45, 0.5],
          scale: 0.15
        }),
        text: new StyleText({
          text: _port.portname,
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
      // offset: [10, 0]
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

}
