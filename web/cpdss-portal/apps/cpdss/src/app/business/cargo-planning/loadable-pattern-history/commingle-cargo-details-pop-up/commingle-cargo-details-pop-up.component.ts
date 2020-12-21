import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { ICommingleCargoDetailsResponse, ICommingleDetails, ICommingleDetailValueObject } from '../../models/cargo-planning.model';
import { ILoadablePatternCargoDetail } from '../../models/loadable-pattern.model';
import { LoadablePatternHistoryApiService } from '../../services/loadable-pattern-history-api.service';
import { LoadableStudyPatternTransformationService } from '../../services/loadable-study-pattern-transformation.service'

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
  @Input() display;

  commingleDetailsForm: FormGroup;
  columns: IDataTableColumn[];
  comingleCargoDetailsData = [];
  commingleDetail: ICommingleDetails;
  comingleCargoDetailForm: FormGroup;

  constructor(private fb: FormBuilder, private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService, private loadablePatternApiService: LoadablePatternHistoryApiService, private ngxSpinnerService: NgxSpinnerService) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof CommingleCargoDetailsPopUpComponent
   */
  async ngOnInit(): Promise<void> {
    this.columns = this.loadableStudyPatternTransformationService.getCommingleDetailsDatatableColumns();
    this.getCommingleCargoDetails(this.vesselId, this.voyageId, this.loadableStudyId, this.selectedLoadablePatterns?.loadablePatternDetailsId, this.selectedLoadablePatterns?.loadablePatternCommingleDetailsId);
  }

  // for closing active modal commingle popup
  closeDialog() {
    this.displayPopup.emit(false);
  }

  /**
 * Method for initializing commingle details row
 * @private
 * @param {ICommingleDetailValueObject} ports
 * @returns
 * @memberof CommingleCargoDetailsPopUpComponent
 */
  private initCommingleDetailFormGroup(commingleDetail: ICommingleDetailValueObject) {
    return this.fb.group({
      id: this.fb.control(commingleDetail.id),
      tankShortName: this.fb.control(commingleDetail.tankShortName),
      cargo1Abbrivation: this.fb.control(commingleDetail.cargo1Abbrivation),
      cargo2Abbrivation: this.fb.control(commingleDetail.cargo2Abbrivation),
      grade: this.fb.control(commingleDetail.grade),
      quantity: this.fb.control(commingleDetail.quantity),
      api: this.fb.control(commingleDetail.api),
      temperature: this.fb.control(commingleDetail.temperature),
      cargo1Quantity: this.fb.control(commingleDetail.cargo1Quantity),
      cargo2Quantity: this.fb.control(commingleDetail.cargo2Quantity),
      cargo1Percentage: this.fb.control(commingleDetail.cargo1Percentage),
      cargo2Percentage: this.fb.control(commingleDetail.cargo2Percentage),
      cargoQuantity: this.fb.control(commingleDetail.cargoQuantity),
      cargoTotalQuantity: this.fb.control(commingleDetail.cargoTotalQuantity),
      cargoPercentage: this.fb.control(commingleDetail.cargoPercentage)
    });
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
      const commingleDetail = this.loadableStudyPatternTransformationService.getCommingleDetailAsValueObject(commingleCargoDetailsResponse, false, false);
      const commingleCargoDetailsArray = [];
      commingleCargoDetailsArray.push(this.initCommingleDetailFormGroup(commingleDetail))
      this.comingleCargoDetailForm = this.fb.group({
        dataTable: this.fb.array([...commingleCargoDetailsArray])
      });
      this.comingleCargoDetailsData.push(commingleDetail)
    }
    this.ngxSpinnerService.hide();
  }
}
