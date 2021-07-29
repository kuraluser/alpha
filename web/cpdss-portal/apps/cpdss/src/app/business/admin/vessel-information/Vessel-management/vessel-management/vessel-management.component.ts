import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

// This code will remove once the actual API is available
const tempData = {
  vesselId: 1,
  vesselName: 'Kazusa',
  vesselImageUrl: 'http://www.aukevisser.nl/supertankers/VLCC%20I-K/2c2384510.jpg',
  countryFlagUrl: 'assets/images/flags/japan.png',
  imoNumber: 9339997,
  generalInfo: {
    owner: 'Mitsui O.S.K Lines, LTD',
    vesselType: 'Cargo oil tanker',
    builder: 'Mitsui engineering & shipbuilding',
    dateOfKeelLaid: '27-12-2014',
    dateOfLaunch: '07-02-2019',
    dateOfDelivery: '10-07-2019',
    navigationArea: 'Ocean going',
    class: 'NS*MNS*'
  },
  vesselDimesnsions: {
    registerLength: 333,
    lengthOverall: 333,
    draftFullLoad: 11.610,
    draftFullLoadInFt: 38.09,
    breadthMolded: 80,
    lengthBetweenPerpendiculars: 324,
    depthMoulded: 28,
    designedLoadDraft: 10.020
  },
  draftDisplacementDeadweight: {
    depthMolded: 20,
    thicknessOfUpperDeck: 0.015,
    thicknessOfKeelPlate: 0.023,
    totalDepth: 14.24
  }
};

/**
 * Component for Vessel details management 
 *
 * @export
 * @class VesselManagementComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-vessel-management',
  templateUrl: './vessel-management.component.html',
  styleUrls: ['./vessel-management.component.scss']
})
export class VesselManagementComponent implements OnInit {

  vesselId: number;
  vesselDetails: any;

  constructor(
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.vesselId = Number(params.get('vesselId'));
    });
    this.getVesselManagementDetails();
  }

  /**
   * Function to get Vessel information in detail
   *
   * @memberof VesselManagementComponent
   */
  getVesselManagementDetails(): void {
    this.vesselDetails = tempData;
  }

}
