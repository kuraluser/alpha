import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import { SynopticalService } from './services/synoptical.service';

/**
 * Component class of synoptical
 *
 * @export
 * @class SynopticalComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-synoptical',
  templateUrl: './synoptical.component.html',
  styleUrls: ['./synoptical.component.scss'],
  providers: [SynopticalService]
})
export class SynopticalComponent implements OnInit {

  constructor(
    private ngxSpinnerService: NgxSpinnerService,
    public synopticalService: SynopticalService,
    private router: Router
  ) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof SynopticalComponent
   */
  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    await this.synopticalService.init();
    this.ngxSpinnerService.hide();
  }

  /**
  * Show loadable study list based on selected voyage id
  */
  onVoyageSelected() {
    this.synopticalService.selectedLoadableStudy = null;
    this.synopticalService.loadableStudyId = null;
    this.synopticalService.selectedLoadablePattern = null;
    this.synopticalService.loadablePatternId = null; 
    this.synopticalService.getLoadableStudyInfo(this.synopticalService.vesselInfo?.id, this.synopticalService.selectedVoyage.id);
    this.router.navigateByUrl('/business/synoptical')
  }

  /**
   * On selecting the loadable study
   *
   * @param event
   * @memberof SynopticalComponent
   */

  onSelectLoadableStudy() {
    this.synopticalService.selectedLoadablePattern = null;
    this.synopticalService.loadablePatternId = null;
    this.synopticalService.getLoadablePatterns();
    this.router.navigateByUrl('/business/synoptical/' + this.synopticalService.vesselInfo.id + '/' + this.synopticalService.selectedVoyage.id + '/' + this.synopticalService.selectedLoadableStudy.id)
  }

  /**
   * On selecting the loadable pattern
   *
   * @param event
   * @memberof SynopticalComponent
   */
  onSelectLoadablePattern() {
    this.router.navigateByUrl('/business/synoptical/' + this.synopticalService.vesselInfo.id + '/' + this.synopticalService.selectedVoyage.id + '/' + this.synopticalService.selectedLoadableStudy.id + '/' + this.synopticalService.selectedLoadablePattern.loadablePatternId)
  }

}
