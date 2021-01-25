import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { QUANTITY_UNIT } from '../../models/common.model';
import { AppConfigurationService } from '../../services/app-configuration/app-configuration.service';

/**
 * Compoent for Unit Dropdown
 *
 * @export
 * @class UnitDropdownComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-unit-dropdown',
  templateUrl: './unit-dropdown.component.html',
  styleUrls: ['./unit-dropdown.component.scss']
})
export class UnitDropdownComponent implements OnInit {

  @Input() showLabel = true;

  @Output() unitChange: EventEmitter<any> = new EventEmitter();

  unitOptions = [
    { value: QUANTITY_UNIT.MT },
    { value: QUANTITY_UNIT.BBLS },
    { value: QUANTITY_UNIT.KL },
  ];
  selectedUnit: { value: string };
  constructor() { }

  // Component lifecycle hook on initialization of component
  ngOnInit(): void {
    const unit = localStorage.getItem('unit');
    if(unit){
      this.setSelected(unit)
      this.unitChange.emit({ unit: this.selectedUnit.value})
    } else {
      this.setSelected(AppConfigurationService.settings.baseUnit)
      this.setUnit();
    }
  }

  // Method called on unit change
  onUnitChange(event) {
    this.setUnit();
    this.unitChange.emit({ unit: this.selectedUnit.value})
  }

  // Method to set the unit in the application
  setUnit(){
    localStorage.setItem('unit',this.selectedUnit.value)
  }

  // Method to set the selected unit
  setSelected(unit){
    this.selectedUnit = { value: unit}
  }

}
