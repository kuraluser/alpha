import { Component, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ICargo, ICargoResponseModel } from '../../../shared/models/common.model';
import { OPERATION_TAB } from '../models/operations.model';
import { LoadingApiService } from '../services/loading-api.service';
import { LoadingTransformationService } from '../services/loading-transformation.service';
import { LoadingInformationComponent } from './loading-information/loading-information.component';

/**
 * Component class for loading component
 *
 * @export
 * @class LoadingComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent implements OnInit, OnDestroy {

  @ViewChild(LoadingInformationComponent) loadingInformationComponent: LoadingInformationComponent

  currentTab: OPERATION_TAB = OPERATION_TAB.INFORMATION;
  OPERATION_TAB = OPERATION_TAB;
  vesselId: number;
  voyageId: number;
  portRotationId: number;
  cargoTanks = [];
  display = false;
  selectedPortName: string;
  loadingInformationComplete: boolean;
  cargos: ICargo[]

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private activatedRoute: ActivatedRoute,
    private loadingTransformationService: LoadingTransformationService,
    private loadingApiService: LoadingApiService) {


  }



  ngOnInit(): void {
    this.initSubsciptions();
    this.activatedRoute.paramMap
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(params => {
        this.getCargos();
        this.vesselId = Number(params.get('vesselId'));
        this.voyageId = Number(params.get('voyageId'));
        this.portRotationId = Number(params.get('portRotationId'));
        localStorage.setItem("vesselId", this.vesselId.toString());
        localStorage.setItem("voyageId", this.voyageId.toString());
        this.selectedPortName = localStorage.getItem('selectedPortName');
      });
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Initialise all subscription in this page
   *
   * @private
   * @memberof LoadingComponent
   */
  private async initSubsciptions() {
    this.loadingTransformationService.loadingInformationValidity$.subscribe((res) => {
      this.loadingInformationComplete = res;
    })
  }

  /**
  * Method for event to save loading information data
  *
  * @memberof LoadingComponent
  */

  saveLoadingInformationData() {
    this.loadingInformationComponent.saveLoadingInformationData();
  }

  /**
* Method to get cargos
*
* @memberof LoadingComponent
*/
  async getCargos() {
    const cargos: ICargoResponseModel = await this.loadingApiService.getCargos().toPromise();
    if (cargos.responseStatus.status === '200') {
      this.cargos = cargos.cargos;
    }
  }

}
