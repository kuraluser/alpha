import { Component, Input, OnInit } from '@angular/core';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IBunkerConditionParameterList, IBunkerConditions } from '../models/voyage-status.model';
/**
 * Component class of ParameterListComponent
 */
@Component({
  selector: 'cpdss-portal-parameter-list',
  templateUrl: './parameter-list.component.html',
  styleUrls: ['./parameter-list.component.scss']
})
export class ParameterListComponent implements OnInit {
  @Input() bunkerConditions :IBunkerConditions;
  cols: IDataTableColumn[];
  parameterList: IBunkerConditionParameterList[] = [];

  constructor() { }
  /**
   * Component lifecycle ngOnit
   *
   * @memberof ParameterListComponent
   */
  ngOnInit(): void {
    this.cols = [
      { field: 'parameters', header: 'VOYAGE_STATUS_CARGO_PARAMETER_LIST' },
      { field: 'value', header: 'VOYAGE_STATUS_CARGO_VALUE' }
    ];
    
    for (const [key, value] of Object.entries(this.bunkerConditions)) {
      this.parameterList.push({ parameters: key, value: value })
    }
  }

}
