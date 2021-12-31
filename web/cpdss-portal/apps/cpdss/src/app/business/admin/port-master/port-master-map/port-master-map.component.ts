import { Component, OnInit, AfterViewInit, Input, Output, EventEmitter } from '@angular/core';
import 'ol/ol.css';
import { Feature, Map, View } from 'ol';
import TileLayer from 'ol/layer/Tile';
import OSM, { ATTRIBUTION } from 'ol/source/OSM';
import VectorLayer from 'ol/layer/Vector';
import Style from 'ol/style/Style';
import VectorSource from 'ol/source/Vector';
import { Draw } from 'ol/interaction';
import * as olProj from 'ol/proj';
import StyleIcon from 'ol/style/Icon';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';

import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';

/**
 * Component class for port master map component
 *
 * @export
 * @class PortMasterMapComponent
 * @implements {OnInit}
 * @implements {AfterViewInit}
 */
@Component({
  selector: 'cpdss-portal-port-master-map',
  templateUrl: './port-master-map.component.html',
  styleUrls: ['./port-master-map.component.scss']
})
export class PortMasterMapComponent implements OnInit, AfterViewInit {

  @Output() coordinates: EventEmitter<any> = new EventEmitter();
  @Output() isModalOpen: EventEmitter<any> = new EventEmitter();

  @Input() currentLatLong: number[];
  @Input() isVisible;

  map: Map;
  view: View;
  tileLayer: TileLayer;
  vectorLayor: VectorLayer;
  minZoom = 2;
  maxZoom = 200;
  typeSelect = null;
  draw: any;
  vectorSource: any;
  visible = false;
  coords = null;
  pinIconSrc = AppConfigurationService.settings?.minimapPinIconUrl;

  constructor() { }

  ngOnInit() {
    this.visible = this.isVisible;
  }

  ngAfterViewInit() {
    this.initMap();
  }

  /**
   * Method to initialise map
   *
   * @memberof FleetMapComponent
   */
  initMap(): void {
    this.vectorSource = this.currentLatLong ? this.markSelectedLocation(this.currentLatLong) : new VectorSource({ wrapX: false });
    const mapCenter = this.currentLatLong ? olProj.fromLonLat([this.currentLatLong[1], this.currentLatLong[0]]) : [9441371.9389, 258337.7609];

    this.view = new View({
      center: mapCenter,
      zoom: 5,
      minZoom: this.minZoom,
      maxZoom: this.maxZoom
    });

    this.tileLayer = new TileLayer({
      source: new OSM({
        url: 'https://tile.thunderforest.com/outdoors/{z}/{x}/{y}.png?apikey=6fb77ea124ac439399a6fd248878f57e'
      })
    });

    this.vectorLayor = new VectorLayer({
      source: this.vectorSource,
      style: new Style({
        image: new StyleIcon({
          crossOrigin: 'anonymous',
          src: this.pinIconSrc,
          anchor: [0.45, 0.5],
          scale: 0.1
        }),
      }),
    });

    this.map = new Map({
      target: 'countryMap',
      layers: [this.tileLayer, this.vectorLayor],
      view: this.view,
      pixelRatio: 1,
    });
    this.addInteraction();
    this.visible = true;

    // Event to get clicked coordinates
    this.map.on('click', (evt) => {
      if (this.vectorSource?.getFeatures()?.length > 1) {
        this.vectorSource.removeFeature(this.vectorSource.getFeatures()[0]);
      }
      this.coords = olProj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326').map(coordinate => (Number(coordinate.toFixed(4))));
    });
  }

  /**
   * Method to add interaction with the map.
   *
   * @memberof PortMasterMapComponent
   */
  addInteraction() {
    this.typeSelect = { value: "Point" };
    this.draw = new Draw({
      source: this.vectorSource,
      type: this.typeSelect.value,
      condition: (e) => {
        // when the point's button is 2(leftclick), prevents drawing
        if ((<any>e.originalEvent).button === 2) {
          return false;
        } else {
          return true;
        }
      }
    });
    this.map.addInteraction(this.draw);
  }

  /**
   * Method to mark selected location in the map
   *
   * @param {*} selectedPortLocation
   * @return {*}  {*}
   * @memberof PortMasterMapComponent
   */
  markSelectedLocation(selectedPortLocation: number[]): any {
    const feature = [];
    const portLocation = new Feature({
      geometry: new Point(fromLonLat([selectedPortLocation[1], selectedPortLocation[0]])),
      objectType: 'marker'
    });
    const portStyleIcon = new Style({
      image: new StyleIcon({
        crossOrigin: 'anonymous',
        src: this.pinIconSrc,
        anchor: [0.45, 0.5],
        scale: 0.1
      })
    });
    portLocation.setStyle(portStyleIcon);
    feature.push(portLocation)
    const vectorSource = new VectorSource({
      features: feature
    });
    return vectorSource;
  }

  /**
   *Method to set the portlocation to the service
   *
   * @memberof PortMasterMapComponent
   */
  setPortLocation(): void {
    if (this.coords) {
      this.coordinates.emit({ lat: this.coords[1], long: this.coords[0] });
    }
    this.isModalOpen.emit(false);
    this.visible = false;
  }

  /**
   * Method to cancel the plotted location from map
   *
   * @memberof PortMasterMapComponent
   */
  onClearLocation(): void {
    this.vectorSource.removeFeature(this.vectorSource.getFeatures()[0]);
    this.coords = null;
  }

  /**
   * Method to trigger value to close the modal
   *
   * @memberof PortMasterMapComponent
   */
  onClose() {
    this.isModalOpen.emit(false);
    this.visible = false;
  }

}



