import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'cpdss-portal-loading-discharging-berth',
  templateUrl: './loading-discharging-berth.component.html',
  styleUrls: ['./loading-discharging-berth.component.scss']
})
/**
 * Component class for loading discharging berth component
 *
 * @export
 * @class LoadingDischargingBerthComponent
 * @implements {OnInit}
 */
export class LoadingDischargingBerthComponent implements OnInit {

  formGroup: FormGroup;
  berthList: any = [];
  berthDropDownlist: any = [];
  constructor(
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.formGroup = this.fb.group({
      berthName: '',
      depth: '',
      manifold: '',
      alongSideHouseConnection: '',
      seaDraftLimitation: '',
      airdraftLimitation: '',
      maxManifoldheight: '',
      specialRegulation: ''

    });
    this.berthDropDownlist = [
      { id: 1, label: 'North Pier #11/#12' },
      { id: 2, label: 'South Pier #11/#12' },
      { id: 3, label: 'West Pier #11/#12' },
      { id: 4, label: 'East Pier #11/#12' }
    ];
  }

  change() {

  }
  clearFilter(data) {

  }

  /**
   * Add new berth
   *
   * @memberof LoadingDischargingBerthComponent
   */
  addBerth() {
    let editMode = false;
    this.berthList.map(item => {
      if (item.edit) {
        editMode = true;
      }
    });
    if (editMode) { return };
    this.berthList.push({ id: this.berthList.length + 1, berth: '', edit: true });
  }

  /**
   * Berth change 
   * @param {Event}
   * @memberof LoadingDischargingBerthComponent
   */
  onBerthChange(event) {
    this.berthList.map(item => { item.edit = false });
    this.setBerthDetails(this.getBerthInfo(event.value));
  }

  /**
   * choose berth 
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  selectBerth(berth) {
    this.setBerthDetails(this.getBerthInfo(berth.berth));
  }

  /**
   * Assign values for berth display
   * @param berthInfo
   * @memberof LoadingDischargingBerthComponent
   */
  setBerthDetails(berthInfo) {
    this.formGroup = this.fb.group({
      berthName: berthInfo.berthName,
      depth: berthInfo.depth,
      manifold: berthInfo.manifold,
      alongSideHouseConnection: berthInfo.alongSideHouseConnection,
      seaDraftLimitation: berthInfo.seaDraftLimitation,
      airdraftLimitation: berthInfo.airdraftLimitation,
      maxManifoldheight: berthInfo.maxManifoldheight,
      specialRegulation: berthInfo.specialRegulation

    });
  }

  /**
   * edit berth info
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  editBerth(berth) {
    this.berthList.map(item => { item.edit = false });
    berth.edit = true;
  }

  /**
   * Remove berth info
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  deleteBerth(berth){
    const index = this.berthList.findIndex( item=> item.id === berth.id);
    this.berthList.splice(index,1);
  }

  /**
   * Dummy data for berth list
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  getBerthInfo(berth) {
    const berthData = [
      {
        id: 1,
        berthName: 'North Pier #11/#12',
        depth: '26.5',
        manifold: '#1,#2,#3',
        alongSideHouseConnection: '',
        seaDraftLimitation: '',
        airdraftLimitation: '',
        maxManifoldheight: '',
        specialRegulation: ''
      },
      {
        id: 2,
        berthName: 'South Pier #11/#12',
        depth: '26.5',
        manifold: '#1,#2,#3',
        alongSideHouseConnection: '',
        seaDraftLimitation: '',
        airdraftLimitation: '',
        maxManifoldheight: '',
        specialRegulation: ''
      },
      {
        id: 4,
        berthName: 'East Pier #11/#12',
        depth: '26.5',
        manifold: '#1,#2,#3',
        alongSideHouseConnection: '',
        seaDraftLimitation: '',
        airdraftLimitation: '',
        maxManifoldheight: '',
        specialRegulation: ''
      },
      {
        id: 3,
        berthName: 'West Pier #11/#12',
        depth: '26.5',
        manifold: '#1,#2,#3',
        alongSideHouseConnection: '',
        seaDraftLimitation: '',
        airdraftLimitation: '',
        maxManifoldheight: '',
        specialRegulation: ''
      }
    ];
    const berthRow = berthData.filter(item => item.id === berth.id);
    return berthRow.length ? berthRow[0] : {};
  }
}
