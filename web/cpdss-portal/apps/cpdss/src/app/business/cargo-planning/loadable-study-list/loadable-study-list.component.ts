import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Table } from 'primeng/table';
import { VesselDetailsModel } from '../../model/vessel-details.model';
import { VesselsApiService } from '../../services/vessels-api.service';

import { LoadableStudies, TableColumns } from '../model/loadable-study-list.model';
import { VoyageList } from '../model/loadable-study-list.model';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';

/**
 * Loadable study list
 */
@Component({
  selector: 'cpdss-portal-loadable-study-list',
  templateUrl: './loadable-study-list.component.html',
  styleUrls: ['./loadable-study-list.component.scss']
})
export class LoadableStudyListComponent implements OnInit {
  loadableStudyList: LoadableStudies[];
  voyageNo: VoyageList[];
  selectedVoyageNo: VoyageList;
  selectedLoadableStudy: LoadableStudies[];
  loading = true;
  cols: TableColumns[];
  display = false;
  duplicatingLoadableStudyId = 'LS1';
  isDuplicateExistingLoadableStudy = true;
  vesselDetails: VesselDetailsModel[];

  @ViewChild('dt') table: Table;

  constructor(private loadableStudyListApiService: LoadableStudyListApiService, private vesselsApiService: VesselsApiService,
    private router: Router, private translateService: TranslateService) { }

  ngOnInit(): void {
    this.vesselDetails = this.vesselsApiService.vesselDetails;
    this.getLoadableStudyInfo();
    this.voyageNo = [
      { name: 'TC-10', code: 'TC-10' },
      { name: 'TC-11', code: 'TC-11' }
    ];
    this.cols = [
      { field: 'name', header: 'LOADABLE_STUDY_LIST_GRID_LOADABLE_STUDY_NAME_LABEL' },
      { field: 'detail', header: 'LOADABLE_STUDY_LIST_GRID_ENQUIRY_DETAILS_LABEL' },
      { field: 'status', header: 'LOADABLE_STUDY_LIST_GRID_STATUS_LABEL' },
      { field: 'createdDate', header: 'LOADABLE_STUDY_LIST_GRID_DATE_LABEL' }
    ];
    this.loading = false;

  }
  /**
   * Called when row is selected
   */
  onRowSelect(event: HTMLInputElement) {
    this.router.navigate(['/business/cargo-planning']);
  }

  /**
   * Filter date
   */
  onDateSelect(value) {
    this.table.filter(this.formatDate(value), 'date', 'equals');
  }

  /**
   * Format date(dd-mm-yyyy)
   */
  formatDate(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();

    if (month < 10) {
      month = '0' + month;
    }

    if (day < 10) {
      day = '0' + day;
    }

    return day + '-' + month + '-' + date.getFullYear();
  }
  /**
   * Get loadable study list
   */
  async getLoadableStudyInfo(): Promise<LoadableStudies[]> {
    this.loadableStudyList = await this.loadableStudyListApiService.getLoadableStudyData();
    return this.loadableStudyList;

  }
  // invoke popup which binds new-loadable-study-popup component
  callNewLoadableStudyPopup() {
    this.display = true;
  }

  // set visibility of popup (show/hide)
  setPopupVisibility(emittedValue) {
    this.display = emittedValue;
  }

}
