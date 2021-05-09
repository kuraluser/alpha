import { Component, OnInit, AfterViewInit, Input, Output, EventEmitter } from '@angular/core';
import 'ol/ol.css';
import { Feature, Map, View } from 'ol';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import VectorLayer from 'ol/layer/Vector';
import Style from 'ol/style/Style';
import VectorSource from 'ol/source/Vector';
import { Draw } from 'ol/interaction';
import * as olProj from 'ol/proj';
import StyleIcon from 'ol/style/Icon';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import { PortMasterTransformationService } from '../../services/port-master-transformation.service';


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

  @Input()
  isVisible;

  map: Map;
  view: View;
  tileLayer: TileLayer;
  vectorLayor: VectorLayer;
  minZoom: number = 3;
  maxZoom: number = 200;
  typeSelect = null;
  draw: any;
  vectorSource: any;
  visible = false;
  coords = null;
  selectedPortLocation: any;

  @Output() coordinates: EventEmitter<any> = new EventEmitter();
  @Output() isModalOpen: EventEmitter<any> = new EventEmitter();

  constructor(private portMasterTransformationService: PortMasterTransformationService) { }

  /**
   * Component lifecycle ngoninit
   *
   * @memberof PortMasterMapComponent
   */
  ngOnInit(): void {
    this.visible = this.isVisible;
  }


  /**
   *Component lifecyle ngafterviewinit
   *
   * @memberof PortMasterMapComponent
   */
  async ngAfterViewInit() {
    this.setVectorSource();
    this.initMap();
    this.listenToMouseClick();
  }


  /**
   *Method to set the vector source.
   *
   * @memberof PortMasterMapComponent
   */
  setVectorSource() {
    this.selectedPortLocation = this.portMasterTransformationService.getSelectedPortLocation();
    this.selectedPortLocation ? this.vectorSource = this.markSelectedLocation(this.selectedPortLocation) : this.vectorSource = new VectorSource({ wrapX: false });
  }

  /**
   * Method to listen to the mouse single click
   *
   * @memberof PortMasterMapComponent
   */
  listenToMouseClick() {
    this.map.on('click', (evt) => {
      this.coords = olProj.toLonLat(evt.coordinate);
      if (this.vectorSource?.getFeatures()?.length > 1) {
        this.vectorSource.removeFeature(this.vectorSource.getFeatures()[0]);
      }
    });
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

  /**
   * Method to initialise map
   *
   * @memberof FleetMapComponent
   */
  async initMap(): Promise<void> {
    this.view = new View({
      center: [9441371.9389, 258337.7609],
      zoom: this.minZoom,
      minZoom: this.minZoom,
      maxZoom: this.maxZoom
    });

    this.tileLayer = new TileLayer({
      source: new OSM()
    });
    this.vectorLayor = new VectorLayer({
      source: this.vectorSource,
      style: new Style({
        image: new StyleIcon({
          crossOrigin: 'anonymous',
          /*
           * this url will replace once actual icon given
           */
          src: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYbkLtt6l3r3_s0IUsmdjobXc0YYNve2gh95gw8DLApPotB5xwKwKQVIxdqL3uQrxPwJs&usqp=CAU',
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
  }

  /**
   * Method to add interaction with the map.
   *
   * @memberof PortMasterMapComponent
   */
  
  addInteraction() {
    this.typeSelect = { value: "Point" }
    this.draw = new Draw({
      source: this.vectorSource,
      type: this.typeSelect.value,
      condition: (e) => {
        // when the point's button is 2(leftclick), prevents drawing
        if ((<any>e.originalEvent).button == 2) {
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
  markSelectedLocation(selectedPortLocation: any): any {
    let feature = [];
    const portLocation = new Feature({
      geometry: new Point(fromLonLat([selectedPortLocation.lat, selectedPortLocation.lon])),
      objectType: 'marker'
    });

    const portStyleIcon = new Style({
      image: new StyleIcon({
        crossOrigin: 'anonymous',
        /*
         * this url will replace once actual icon given
         */
        src: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYbkLtt6l3r3_s0IUsmdjobXc0YYNve2gh95gw8DLApPotB5xwKwKQVIxdqL3uQrxPwJs&usqp=CAU',
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

  setPortLocation() {
    this.isModalOpen.emit(false);
    this.visible = false;
    if (this.coords) {
      this.portMasterTransformationService.setSelectedLocation(this.coords);
      this.coordinates.emit({ lat: this.coords[1], lon: this.coords[0] });
    }
  }

  /**
   * Method to cancel the plotted location from map 
   *
   * @memberof PortMasterMapComponent
   */
  onCancelClick() {
    this.vectorSource.removeFeature(this.vectorSource.getFeatures()[0]);
    this.coords = null;
    this.portMasterTransformationService.setSelectedLocation({ lat: null, lon: null });
    this.coordinates.emit({ lat: null, lon: null });
  }
}



