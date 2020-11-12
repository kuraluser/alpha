import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DATATABLE_SELECTIONMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { Voyage } from '../../../core/models/common.models';
import { VesselsApiService } from '../../../core/services/vessels-api.service';
import { VesselDetailsModel } from '../../../model/vessel-details.model';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { LoadableStudyListApiService } from '../../services/loadable-study-list-api.service';

@Component({
  selector: 'cpdss-portal-side-panel-loadable-study-list',
  templateUrl: './side-panel-loadable-study-list.component.html',
  styleUrls: ['./side-panel-loadable-study-list.component.scss']
})
export class SidePanelLoadableStudyListComponent implements OnInit {

  @Input()
  get loadableStudies(): LoadableStudy[] {
    return this._loadableStudies;
  }
  set loadableStudies(loadableStudies: LoadableStudy[]) {
    this._loadableStudies = loadableStudies;
  }

  @Input() vesselId: number;
  @Input() voyage: Voyage;
  @Input() selectedLoadableStudy: LoadableStudy;

  @Output() selectedLoadableStudyChange = new EventEmitter<LoadableStudy>();
  @Output() deleteLoadableStudy = new EventEmitter<Event>();

  columns: IDataTableColumn[];
  vesselInfo: VesselDetailsModel[];
  display = false;
  duplicateLoadableStudy: LoadableStudy;
  selectionMode = DATATABLE_SELECTIONMODE.SINGLE;


  private _loadableStudies: LoadableStudy[];

  constructor(private vesselsApiService: VesselsApiService,
    private loadableStudyListApiService: LoadableStudyListApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService) { }

  ngOnInit(): void {
    this.getGridColumns();
    this.getVesselInfo();
  }

  /**
   * Get vessel info
   *
   * @memberof SidePanelLoadableStudyListComponent
   */
  async getVesselInfo() {
    const result = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = result ?? [];
  }

  /**
   * Get all grid columns
   *
   * @memberof SidePanelLoadableStudyListComponent
   */
  getGridColumns() {
    this.columns = this.loadableStudyDetailsTransformationService.getLoadableStudyGridColumns();
  }

  /**
   * Handler for row duplicate event
   *
   * @param {*} event
   * @memberof SidePanelLoadableStudyListComponent
   */
  onDuplicate(event) {
    this.duplicateLoadableStudy = event?.data;
    this.openLoadableStudyPopup();
  }

  /**
   * Handler for row delete event
   *
   * @param {*} event
   * @memberof SidePanelLoadableStudyListComponent
   */
  onDelete(event) {
    const result = this.loadableStudyListApiService.deleteLodableStudy(this.vesselId, this.voyage?.id, event?.data?.id).toPromise();
    this.deleteLoadableStudy.emit(event);
  }

  /**
   * Open create new loadable study popup
   *
   * @memberof SidePanelLoadableStudyListComponent
   */
  openLoadableStudyPopup() {
    this.display = true;
  }

  /**
   * Set visibility of popup
   *
   * @param {*} event
   * @memberof SidePanelLoadableStudyListComponent
   */
  closeLoadableStudyPopup(event) {
    this.display = event;
  }

  /**
   * Handler for row selection
   *
   * @param {*} event
   * @memberof SidePanelLoadableStudyListComponent
   */
  onRowSelection(event) {
    this.selectedLoadableStudy = event?.data;
    this.selectedLoadableStudyChange.emit(event?.data);
  }
}
