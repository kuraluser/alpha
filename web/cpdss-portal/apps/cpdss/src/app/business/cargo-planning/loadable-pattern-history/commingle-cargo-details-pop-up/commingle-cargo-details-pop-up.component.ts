import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { ICommingleCargoDetailsResponse, ICommingleDetails } from '../../models/cargo-planning.model';
import { ILoadablePatternCargoDetail } from '../../models/loadable-pattern.model';
import { LoadablePatternHistoryApiService } from '../../services/loadable-pattern-history-api.service';
import { LoadableStudyPatternTransformationService } from '../../services/loadable-study-pattern-transformation.service'
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';

/**
 * Component for priority grid
 *
 * @export
 * @class CommingleCargoDetailsPopUpComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-commingle-cargo-details-pop-up',
  templateUrl: './commingle-cargo-details-pop-up.component.html',
  styleUrls: ['./commingle-cargo-details-pop-up.component.scss']
})
export class CommingleCargoDetailsPopUpComponent implements OnInit {

  @Output() displayPopup = new EventEmitter();

  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;
  @Input() selectedLoadablePatterns: ILoadablePatternCargoDetail;
  @Input() loadablePatternDetailsId: number;
  @Input() display;
  @Input() currentQuantitySelectedUnit: QUANTITY_UNIT;

  commingleDetailsForm: FormGroup;
  columns: IDataTableColumn[];
  comingleCargoDetailsData;
  commingleDetail: ICommingleDetails;

  constructor(
    private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService,
    private loadablePatternApiService: LoadablePatternHistoryApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private quantityPipe: QuantityPipe) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof CommingleCargoDetailsPopUpComponent
   */
  ngOnInit() {
    this.columns = this.loadableStudyPatternTransformationService.getCommingleDetailsDatatableColumns();
    this.getCommingleCargoDetails(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternDetailsId, this.selectedLoadablePatterns?.loadablePatternCommingleDetailsId);
  }

  // for closing active modal commingle popup
  closeDialog() {
    this.displayPopup.emit(false);
  }

  /**
  * Fetch loadable study details
  *
  * @param {number} vesselId
  * @param {number} voyageId
  * @param {number} loadableStudyId
  * @param {number} loadablePatternId
  * @param {number} loadablePatternCommingleDetailsId
  * @memberof CommingleCargoDetailsPopUpComponent
  */
  async getCommingleCargoDetails(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number, loadablePatternCommingleDetailsId: number) {
    this.ngxSpinnerService.show();
    const commingleCargoDetailsResponse: ICommingleCargoDetailsResponse = await this.loadablePatternApiService.getCommingleCargoDetails(vesselId, voyageId, loadableStudyId, loadablePatternId, loadablePatternCommingleDetailsId).toPromise();
    if (commingleCargoDetailsResponse.responseStatus.status === '200') {
      this.comingleCargoDetailsData = [commingleCargoDetailsResponse];
      this.convertQuantityToSelectedUnit();
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Method for converting quantities to slected unit
   *
   * @memberof CommingleCargoDetailsPopUpComponent
   */
  convertQuantityToSelectedUnit() {
    const comingleCargoDetailsData = this.comingleCargoDetailsData.map((cargo: ICommingleCargoDetailsResponse) => {
      const cargo1Quantity = this.quantityPipe.transform(cargo.cargo1Quantity, AppConfigurationService.settings.baseUnit, this.currentQuantitySelectedUnit, cargo?.api);
      cargo.cargo1Quantity = cargo1Quantity ? Number(cargo1Quantity.toFixed(2)) : 0;
      const cargo2Quantity = this.quantityPipe.transform(cargo.cargo2Quantity, AppConfigurationService.settings.baseUnit, this.currentQuantitySelectedUnit, cargo?.api);
      cargo.cargo2Quantity = cargo1Quantity ? Number(cargo2Quantity.toFixed(2)) : 0;
      const quantity = this.quantityPipe.transform(cargo.quantity, AppConfigurationService.settings.baseUnit, this.currentQuantitySelectedUnit, cargo?.api);
      cargo.quantity = quantity ? Number(quantity.toFixed(2)) : 0;
      return cargo;
    });

    this.comingleCargoDetailsData = [this.loadableStudyPatternTransformationService.formatCommingleDetail(comingleCargoDetailsData[0])];
  }
}
