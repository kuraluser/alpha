import { Component, OnInit } from '@angular/core';
import { OPERATION_TAB } from '../models/operations.model';

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
export class LoadingComponent implements OnInit {

  currentTab: OPERATION_TAB = OPERATION_TAB.SEQUENCE;
  OPERATION_TAB = OPERATION_TAB;

  constructor() { }
  
  
  cargoTanks = [];
  display = false;

  ngOnInit(): void {   
    
  }

}
