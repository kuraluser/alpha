import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { VesselDetailsModel } from '../../model/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { LoadableStudy, TableColumns } from '../models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';
import { Voyage } from '../../core/models/common.model';
import { VoyageService } from '../../core/services/voyage.service';
import { LoadableStudyListTransformationService } from '../services/loadable-study-list-transformation.service';
import { IDataTableColumn, IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';

/**
 * Loadable study list
 */
@Component({
  selector: 'cpdss-portal-loadable-study-list',
  templateUrl: './loadable-study-list.component.html',
  styleUrls: ['./loadable-study-list.component.scss']
})
export class LoadableStudyListComponent implements OnInit {
  loadableStudyList: LoadableStudy[];
  voyages: Voyage[];
  selectedVoyage: Voyage;
  selectedLoadableStudy: LoadableStudy[];
  loading = true;
  cols: TableColumns[];
  display = false;
  isDuplicateExistingLoadableStudy = true;
  vesselDetails: VesselDetailsModel;
  voyageId: number;
  columns: IDataTableColumn[];
  loadableStudyListForm: FormGroup;
  readonly editMode = null;
  isVoyageIdSelected = true;


  constructor(private loadableStudyListApiService: LoadableStudyListApiService,
    private vesselsApiService: VesselsApiService, private router: Router,
    private translateService: TranslateService, private activatedRoute: ActivatedRoute,
    private voyageService: VoyageService, private loadableStudyListTransformationService: LoadableStudyListTransformationService,
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService) { }

  async ngOnInit(): Promise<void> {
    this.activatedRoute.params.subscribe(async params => {
      this.voyageId = params.id ? Number(params.id): 0;
      this.ngxSpinnerService.show();
      const res = await this.vesselsApiService.getVesselsInfo().toPromise();
      this.vesselDetails = res[0] ?? <VesselDetailsModel>{};
      this.voyages = await this.voyageService.getVoyagesByVesselId(this.vesselDetails?.id).toPromise();
      this.ngxSpinnerService.hide();
      this.getLoadableStudyInfo(this.vesselDetails?.id, this.voyageId);
      this.selectedVoyage = this.voyages.find(voyage => voyage.id === this.voyageId);
    })
    this.columns = this.loadableStudyListTransformationService.getLoadableStudyListDatatableColumns();
    this.loading = false;

  }
  /**
   * Take the user to particular loadable study
   */
  onRowSelect(event: IDataTableEvent) {
    this.router.navigate([`/business/cargo-planning/loadable-study-details/${this.vesselDetails?.id}/${this.selectedVoyage.id}/${event.data.id}`]);
  }

  /**
   * Get loadable study list
   */
  async getLoadableStudyInfo(vesselId: number, voyageId: number) {
    this.ngxSpinnerService.show();
    if (voyageId !== 0) {
      const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
      this.loadableStudyList = result.loadableStudies;
      this.initLoadableStudyArray(this.loadableStudyList);
    }
    this.ngxSpinnerService.hide();
  }

  // invoke popup which binds new-loadable-study-popup component
  callNewLoadableStudyPopup() {
    if (this.selectedVoyage) {
      this.display = true;
    }
    else
      this.isVoyageIdSelected = false;
  }

  // set visibility of popup (show/hide)
  setPopupVisibility(emittedValue) {
    this.display = emittedValue;
  }

  private initLoadableStudyArray(loadableStudyLists: LoadableStudy[]) {
    this.loadableStudyListForm = this.fb.group({
      dataTable: this.fb.array([...loadableStudyLists])
    });
  }

  /**
  * Method for fetching form fields
  *
  * @private
  * @param {number} formGroupIndex
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof 
  */
  private field(formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.loadableStudyListForm.get('dataTable')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
   * Show loadable study list based on selected voyage id
   */
  showLoadableStudyList() {
    this.isVoyageIdSelected = true;
    this.getLoadableStudyInfo(this.vesselDetails?.id, this.selectedVoyage.id);
  }

  /**
   * called when name is clicked
   */
  columnClick(data: IDataTableEvent) {
    if (data?.field === 'name') {
      this.onRowSelect(data);
    }
  }

    /**
   * Handler for added new loadable study
   *
   * @param {*} event
   * @memberof LoadableStudyListComponent
   */
  onNewLoadableStudyAdded(event) {
    this.router.navigate(['business/cargo-planning/loadable-study-details/' + this.vesselDetails?.id + '/' + this.selectedVoyage.id + '/' + event]);
  }

}
