import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { DecimalPipe } from '@angular/common';

import { IPermission } from '../../../../shared/models/user-profile.model';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { Voyage } from '../../../core/models/common.model';
import { ILoadableQuantityCommingleCargo, ICommingleCargoDispaly, IBillingFigValueObject, IBillingOfLaddings } from '../../models/discharge-study-list.model';
import { DischargeStudyDetailsTransformationService } from '../../services/discharge-study-details-transformation.service';
import { DischargeStudyDetailsApiService } from '../../services/discharge-study-details-api.service';


/**
 * Component class of cargo nomination  screen
 *
 * @export
 * @class CargoNominationComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-nomination',
  templateUrl: './cargo-nomination.component.html',
  styleUrls: ['./cargo-nomination.component.scss']
})
export class CargoNominationComponent implements OnInit {

  @Input() voyageId: number;

  @Input() voyage: Voyage;

  @Input()
  get dischargeStudyId() {
    return this._dischargeStudyId;
  }
  set dischargeStudyId(value: number) {
    this._dischargeStudyId = value;
  }

  @Input() permission: IPermission;


  @Input() vesselId: number;
  @Input() ports: number;

  loadableQuantityCargoObjectValue: ICommingleCargoDispaly[];

  @Output() cargoNominationUpdate = new EventEmitter<any>();

  private _dischargeStudyId: number;

  columns: IDataTableColumn[];
  loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[];

  billOfLaddingsValueObject: IBillingFigValueObject[] = [];
  cargoColumns: any;
  listData: any;

  constructor(
    private decimalPipe: DecimalPipe,
    private dischargeStudyDetailsApiService: DischargeStudyDetailsApiService,
    private dischargeStudyDetailsTransformationService: DischargeStudyDetailsTransformationService
  ) { }

  /**
 * Component class for loadable study details component
 *
 * @export
 * @class CargoNominationComponent
 * @implements {OnInit}
 */
  ngOnInit(): void {
    this.columns = this.dischargeStudyDetailsTransformationService.getBFTableColumns();
    this.cargoColumns = this.dischargeStudyDetailsTransformationService.getCommingleDetailsDatatableColumns();
    this.getCargoNominationDetails();
  }

  /**
   * arrange Commingle CargoDetails 
   * @param { ILoadableQuantityCommingleCargo } loadableQuantityCargoDetail
   * @memberof CargoNominationComponent
  */
  arrangeCommingleCargoDetails(loadableQuantityCargoDetail: ILoadableQuantityCommingleCargo) {
    return this.dischargeStudyDetailsTransformationService.getFormatedLoadableCommingleCargo(this.decimalPipe, loadableQuantityCargoDetail);
  }

  /**
 * Get all details for cargonomination screen
 *
 * @returns {Promise<ICargoNominationDetailsResponse>}
 * @memberof CargoNominationComponent
 */
  async getCargoNominationDetails() {
    const result = await this.dischargeStudyDetailsApiService.getCargoNominationDetails(this.vesselId, this.voyageId,this.dischargeStudyId).toPromise();
    this.listData = {
      portsList: this.ports
    }
    const billOfLaddings = result.billOfLaddings;
    const loadableQuantityCommingleCargoDetails = result.loadableQuantityCommingleCargoDetails; 
    const billOfLaddingsValueObject = [];
    billOfLaddings.map(billOfLadding => {
      billOfLaddingsValueObject.push(this.dischargeStudyDetailsTransformationService.getFormatedBillingDetails(billOfLadding, this.listData))
    })
    this.billOfLaddingsValueObject = [...billOfLaddingsValueObject];
    const loadableQuantityCargoObjectValue = [];
    loadableQuantityCommingleCargoDetails?.map((loadableQuantityCargoDetail) => {
      loadableQuantityCargoObjectValue.push(this.arrangeCommingleCargoDetails(loadableQuantityCargoDetail))
    });
    this.loadableQuantityCargoObjectValue = [...loadableQuantityCargoObjectValue];
  }
}
