import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoadableQuantityApiService } from '../../services/loadable-quantity-api.service';
import { LodadableQuantity } from '../../models/loadable-quantity.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { IPort } from '../../models/cargo-planning.model';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { Voyage } from '../../../core/models/common.models';
import { numberValidator } from '../../directives/validator/number-validator.directive';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

/**
 *  popup for loadable quantity
 */
@Component({
  selector: 'cpdss-portal-loadable-quantity',
  templateUrl: './loadable-quantity.component.html',
  styleUrls: ['./loadable-quantity.component.scss']
})
export class LoadableQuantityComponent implements OnInit {

  @Input() selectedLoadableStudy: LoadableStudy;
  @Input() vesselId: number;
  @Input() voyage: Voyage;
  @Output() displayPopUp = new EventEmitter<boolean>();
  @Output() newLoadableQuantity = new EventEmitter<string>();

  display = true;
  loadableQuantityForm: FormGroup;
  portData: any[];
  selectedPort: IPort;
  isSummerZone: boolean;
  loadableQuantity: LodadableQuantity;
  ports: IPort[];
  lastUpdatedDateAndTime: string;
  totalLoadableQuantity: number;

  private _loadableStudies: LoadableStudy[];

  constructor(private fb: FormBuilder,
    private loadableQuantityApiService: LoadableQuantityApiService,
    private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private translateService: TranslateService,
  ) { }

  async ngOnInit(): Promise<void> {
    this.ports = await this.getPorts();
    this.getLoadableQuantity();
  }

  /**
   * Get Loadable Quantity
   */
  async getLoadableQuantity() {
    this.ngxSpinnerService.show();
    const loadableQuantityResult = await this.loadableQuantityApiService.getLoadableQuantity(this.vesselId, this.voyage.id, this.selectedLoadableStudy.id).toPromise();  
    if (loadableQuantityResult.responseStatus.status === '200') {
      this.isSummerZone = loadableQuantityResult.isSummerZone;
      this.loadableQuantity = loadableQuantityResult.loadableQuantity;
      this.selectedPort = this.ports.find(port => port.id === this.loadableQuantity.portId);
      this.lastUpdatedDateAndTime = this.loadableQuantity.updateDateAndTime;

      this.loadableQuantityForm = this.fb.group({
        portName: [this.ports.find(port => port.id === this.loadableQuantity.portId), Validators.required],
        arrivalMaxDraft: ['', [Validators.required, numberValidator(2, 2)]],
        dwt: ['', [Validators.required]],
        tpc: ['', [Validators.required, numberValidator(1, 1)]],
        estimateSag: ['', [Validators.required, numberValidator(2, 2)]],
        safCorrection: ['', [Validators.required]],
        foOnboard: ['', [Validators.required, numberValidator(0, 4)]],
        doOnboard: ['', [Validators.required, numberValidator(0, 4)]],
        freshWaterOnboard: ['', [Validators.required, numberValidator(0, 4)]],
        boilerWaterOnboard: ['', [Validators.required, numberValidator(0, 4)]],

        ballast: ['', [Validators.required, numberValidator(0, 2)]],
        constant: ['', [Validators.required]],
        others: ['', [Validators.required, numberValidator(0, 2)]],
        subTotal: ['', Validators.required],
        totalQuantity: ['', Validators.required]

      });

      if (this.isSummerZone) {
        this.loadableQuantityForm.addControl('distanceInSummerzone', this.fb.control('',[ Validators.required, numberValidator(0, 2)]));
        this.loadableQuantityForm.addControl('speedInSz', this.fb.control('',[ Validators.required, numberValidator(0, 2)]));
        this.loadableQuantityForm.addControl('runningHours', this.fb.control('',[ Validators.required, numberValidator(0, 2)]));
        this.loadableQuantityForm.addControl('runningDays', this.fb.control('',[ Validators.required, numberValidator(0, 2)]));
        this.loadableQuantityForm.addControl('foConday', this.fb.control('',[ Validators.required, numberValidator(0, 2)]));
        this.loadableQuantityForm.addControl('foConsInSz', this.fb.control('',[ Validators.required, numberValidator(0, 2)]));
      }
      else {
        this.loadableQuantityForm.addControl('displacement', this.fb.control('',[ Validators.required]));
        this.loadableQuantityForm.addControl('lwt', this.fb.control('',[ Validators.required]));
        this.loadableQuantityForm.addControl('estSeaDensity', this.fb.control('',[ Validators.required]));
        this.loadableQuantityForm.addControl('sgCorrection', this.fb.control('',[ Validators.required]));
      }

     this.getLoadableQuantityData();

    }
    this.ngxSpinnerService.hide();
    
  }

  /**
   * Populate loadable quantity data
   */
  getLoadableQuantityData(){
 
    this.loadableQuantityForm.controls.portName.setValue(this.ports.find(port => port.id === this.loadableQuantity.portId));
    this.loadableQuantityForm.controls.arrivalMaxDraft.setValue(this.loadableQuantity.draftRestriction);
    this.loadableQuantityForm.controls.dwt.setValue(this.loadableQuantity.dwt);
    this.loadableQuantityForm.controls.tpc.setValue(this.loadableQuantity.tpc);
    this.loadableQuantityForm.controls.estimateSag.setValue(this.loadableQuantity.estSagging);
    this.loadableQuantityForm.controls.safCorrection.setValue(this.loadableQuantity.estFOOnBoard);
    this.loadableQuantityForm.controls.foOnboard.setValue(this.loadableQuantity.estFOOnBoard);
    this.loadableQuantityForm.controls.doOnboard.setValue(this.loadableQuantity.estDOOnBoard);

    this.loadableQuantityForm.controls.freshWaterOnboard.setValue(this.loadableQuantity.estFreshWaterOnBoard);
    this.loadableQuantityForm.controls.boilerWaterOnboard.setValue(this.loadableQuantity.boilerWaterOnBoard);
    this.loadableQuantityForm.controls.ballast.setValue(this.loadableQuantity.ballast);
    this.loadableQuantityForm.controls.constant.setValue(this.loadableQuantity.constant);
    this.loadableQuantityForm.controls.others.setValue(this.loadableQuantity.otherIfAny);
    this.loadableQuantityForm.controls.subTotal.setValue(this.loadableQuantity.subTotal);
    this.loadableQuantityForm.controls.totalQuantity.setValue(this.loadableQuantity.totalQuantity);

    if(this.isSummerZone){
      this.loadableQuantityForm.controls.distanceInSummerzone.setValue(this.loadableQuantity.distanceFromLastPort);
      this.loadableQuantityForm.controls.speedInSz.setValue(this.loadableQuantity.vesselAverageSpeed);
      this.loadableQuantityForm.controls.runningHours.setValue(this.loadableQuantity.runningHours);
      this.loadableQuantityForm.controls.runningDays.setValue(this.loadableQuantity.runningDays);
      this.loadableQuantityForm.controls.foConday.setValue(this.loadableQuantity.foConsumptionPerDay);
      this.loadableQuantityForm.controls.foConsInSz.setValue(this.loadableQuantity.foConInSZ);

      this.getRunningHoursOnLoad();
      this.getRunningDaysOnLoad();
      this.getSubTotalOnLoad();
    }
    else{
      this.loadableQuantityForm.controls.displacement.setValue(this.loadableQuantity.displacmentDraftRestriction);
      this.loadableQuantityForm.controls.lwt.setValue(this.loadableQuantity.vesselLightWeight);
      this.loadableQuantityForm.controls.estSeaDensity.setValue(this.loadableQuantity.estSeaDensity);
      this.loadableQuantityForm.controls.sgCorrection.setValue(this.loadableQuantity.sgCorrection);

      this.getDWT();
      this.getSubTotalOnLoad();
    }

  }
  /**
   * Return the form controlls of the form
   */
  get loadableQuantityFormControl() {
    return this.loadableQuantityForm.controls;
  }

  /**
   * save loadable quantity
   */
  async onSubmit() {
    if (this.loadableQuantityForm.valid) {
      this.ngxSpinnerService.show();
      if (this.isSummerZone) {
        this.loadableQuantity = {

          foConInSZ: this.loadableQuantityForm.controls.foConsInSz.value,
          portId: this.loadableQuantityForm.controls.portName.value.id,
          draftRestriction: this.loadableQuantityForm.controls.arrivalMaxDraft.value,
          dwt: this.loadableQuantityForm.controls.dwt.value,
          tpc: this.loadableQuantityForm.controls.tpc.value,
          estSagging: this.loadableQuantityForm.controls.estimateSag.value,
          saggingDeduction: this.loadableQuantityForm.controls.safCorrection.value,
          estFOOnBoard: this.loadableQuantityForm.controls.foOnboard.value,
          estDOOnBoard: this.loadableQuantityForm.controls.doOnboard.value,
          estFreshWaterOnBoard: this.loadableQuantityForm.controls.freshWaterOnboard.value,
          ballast: this.loadableQuantityForm.controls.ballast.value,
          boilerWaterOnBoard: this.loadableQuantityForm.controls.boilerWaterOnboard.value,
          constant: this.loadableQuantityForm.controls.constant.value,
          otherIfAny: this.loadableQuantityForm.controls.others.value,
          distanceFromLastPort: this.loadableQuantityForm.controls.distanceInSummerzone.value,
          vesselAverageSpeed: this.loadableQuantityForm.controls.speedInSz.value,
          runningHours: this.loadableQuantityForm.controls.runningHours.value,
          runningDays: this.loadableQuantityForm.controls.runningDays.value,
          foConsumptionPerDay: this.loadableQuantityForm.controls.foConday.value,
          estTotalFOConsumption: this.loadableQuantityForm.controls.foConsInSz.value,
          totalQuantity: this.loadableQuantityForm.controls.totalQuantity.value,
        }

      }
      else {
        this.loadableQuantity = {
          portId: this.loadableQuantityForm.controls.portName.value.id,
          draftRestriction: this.loadableQuantityForm.controls.arrivalMaxDraft.value,
          displacmentDraftRestriction: this.loadableQuantityForm.controls.displacement.value,
          vesselLightWeight: this.loadableQuantityForm.controls.lwt.value,
          dwt: this.loadableQuantityForm.controls.dwt.value,
          tpc: this.loadableQuantityForm.controls.tpc.value,
          estSeaDensity: this.loadableQuantityForm.controls.estSeaDensity.value,
          sgCorrection: this.loadableQuantityForm.controls.sgCorrection.value,
          estSagging: this.loadableQuantityForm.controls.estimateSag.value,
          saggingDeduction: this.loadableQuantityForm.controls.safCorrection.value,
          estFOOnBoard: this.loadableQuantityForm.controls.foOnboard.value,
          estDOOnBoard: this.loadableQuantityForm.controls.doOnboard.value,
          estFreshWaterOnBoard: this.loadableQuantityForm.controls.freshWaterOnboard.value,
          ballast: this.loadableQuantityForm.controls.ballast.value,
          boilerWaterOnBoard: this.loadableQuantityForm.controls.boilerWaterOnboard.value,
          constant: this.loadableQuantityForm.controls.constant.value,
          otherIfAny: this.loadableQuantityForm.controls.others.value,
          totalQuantity: this.loadableQuantityForm.controls.totalQuantity.value,
        }
      }
      const translationKeys = await this.translateService.get(['VOYAGE_CREATE_SUCCESS', 'VOYAGE_CREATED_SUCCESSFULLY', 'VOYAGE_CREATE_ERROR', 'VOYAGE_ALREADY_EXIST']).toPromise();
      const result = await this.loadableQuantityApiService.saveLoadableQuantity(this.vesselId, this.voyage.id, this.selectedLoadableStudy.id, this.loadableQuantity).toPromise();
      this.newLoadableQuantity.emit(this.loadableQuantity.totalQuantity);
      if (result.responseStatus.status === "200") {
        this.messageService.add({ severity: 'success', summary: translationKeys['VOYAGE_CREATE_SUCCESS'], detail: translationKeys['LOADABLE_QUANTITY_SAVED_SUCCESSFULLY'] });

      }
      this.ngxSpinnerService.hide();
      this.cancel();
    }
    else {
      this.loadableQuantityForm.markAllAsTouched();
    }
  }

  /**
   * Cancel popup
   */
  cancel() {
    this.displayPopUp.emit(false);
  }

  /**
   * get ports
   */
  private async getPorts(): Promise<IPort[]> {
    const result = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    return result;
  }

  /**
   * get DWT
   */
  getDWT() {
    const dwt = (Number(this.loadableQuantity.displacmentDraftRestriction) - Number(this.loadableQuantity.vesselLightWeight));
    this.loadableQuantityForm.controls['dwt'].setValue(dwt);

  }

  /**
   * Calculation for sag correction
   */
  getSagCorrectionOnChange() {
    this.loadableQuantityForm.controls['safCorrection'].setValue((this.loadableQuantityForm.get('estimateSag').value) * 1 / 4 * (this.loadableQuantityForm.get('tpc').value));
    this.getSubTotal();
  }
  /**
   * Calculation for sg correction
   */
  getSgCorrectionOnChange() {
    this.loadableQuantityForm.controls['sgCorrection'].setValue(((this.loadableQuantityForm.get('estSeaDensity').value) - 1.025) / 1.025 * (this.loadableQuantityForm.get('displacement').value));
    this.getSubTotal();
  }

  /**
   * Calculation for running hours
   */
  getRunningHours() {
    // Auto calculate (Distance/ Speed)
    this.loadableQuantityForm.controls['runningHours'].setValue((this.loadableQuantityForm.get('distanceInSummerzone').value) / (this.loadableQuantityForm.get('speedInSz').value));
    this.getRunningDays();
  }

  /**
   * Calculation for running hours
   * Auto calculate (Distance/ Speed)
   */
  getRunningHoursOnLoad() {
    this.loadableQuantityForm.controls['runningHours'].setValue(Number(this.loadableQuantity.distanceFromLastPort) / Number(this.loadableQuantity.vesselAverageSpeed));
  }

  /**
   * Calculation for running days
   * Auto calculate (Time/24)
   */
  getRunningDays() {
    if (this.loadableQuantityForm?.get('runningHours')?.value) {
      this.loadableQuantityForm.controls['runningDays'].setValue((this.loadableQuantityForm.get('runningHours').value) / 24);
      this.getFoConsumptionInSz();
    }
  }
  /**
   * Calculation for running days
   */
  getRunningDaysOnLoad() {
    this.loadableQuantityForm.controls['runningDays'].setValue(Number(this.loadableQuantity.runningHours) / 24);
    this.loadableQuantityForm.controls['foConsInSz'].setValue((this.loadableQuantityForm.get('foConday').value) * (this.loadableQuantityForm.get('runningDays').value));
    this.getSubTotalOnLoad();
  }

  /**
   * Calculation for fo consumption in sz
   */
  getFoConsumptionInSz() {
    this.loadableQuantityForm.controls['foConsInSz'].setValue((this.loadableQuantityForm.get('foConday').value) * (this.loadableQuantityForm.get('runningDays').value));
    this.getTotalLoadableQuantity();

  }

  /**
   * Calculation for subtotal
   */
  getSubTotal() {
    let subTotal = 0;
    if (!this.isSummerZone) {
      subTotal = (this.loadableQuantityForm.get('dwt').value) + (this.loadableQuantityForm.get('sgCorrection').value)
        + (this.loadableQuantityForm.get('safCorrection').value)
        - (this.loadableQuantityForm.get('foOnboard').value) - (this.loadableQuantityForm.get('doOnboard').value)
        - (this.loadableQuantityForm.get('freshWaterOnboard').value) - (this.loadableQuantityForm.get('boilerWaterOnboard').value)
        - (this.loadableQuantityForm.get('ballast').value) - (this.loadableQuantityForm.get('constant').value)
        - (this.loadableQuantityForm.get('others').value);
      this.loadableQuantityForm.controls['subTotal'].setValue(subTotal);
      this.getTotalLoadableQuantity();
    }
    else {
      subTotal = (this.loadableQuantityForm.get('dwt').value) + (this.loadableQuantityForm.get('safCorrection').value)
        - (this.loadableQuantityForm.get('foOnboard').value) - (this.loadableQuantityForm.get('doOnboard').value) - (this.loadableQuantityForm.get('freshWaterOnboard').value) - (this.loadableQuantityForm.get('boilerWaterOnboard').value) - (this.loadableQuantityForm.get('ballast').value) - (this.loadableQuantityForm.get('constant').value) - (this.loadableQuantityForm.get('others').value);
    }
    this.loadableQuantityForm.controls['subTotal'].setValue(subTotal);
  }

  /**
   * Calculation for subtotal
   */
  getSubTotalOnLoad() {
    if (this.isSummerZone) {
      this.loadableQuantityForm.controls['subTotal'].setValue(Number(this.loadableQuantity.totalQuantity) + this.loadableQuantityForm.get('foConsInSz').value);
    }
    else {
      this.loadableQuantityForm.controls['subTotal'].setValue(this.loadableQuantity.totalQuantity);
    }
  }

  /**
   * Calculation for Loadable quantity
   */
  getTotalLoadableQuantity() {
    if (this.isSummerZone) {
      this.loadableQuantityForm.controls['totalQuantity'].setValue((this.loadableQuantityForm.get('subTotal').value) - (this.loadableQuantityForm.get('foConsInSz').value));
    }

    else {
      this.loadableQuantityForm.controls['totalQuantity'].setValue((this.loadableQuantityForm.get('subTotal').value));
    }
  }

}
