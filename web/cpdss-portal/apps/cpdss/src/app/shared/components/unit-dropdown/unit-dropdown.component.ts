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

  @Input() disableUnitChange = false;

  @Input() selectedUnit: { value: string };

  @Input()
  set isObsTemp(value: boolean) {
    this._isObsTemp = value;

    if(value) {
      this.unitOptions = [
        { value: QUANTITY_UNIT.OBSBBLS },
        { value: QUANTITY_UNIT.OBSKL }
      ];
    } else {
      this.unitOptions = [
        { value: QUANTITY_UNIT.MT },
        { value: QUANTITY_UNIT.BBLS },
        { value: QUANTITY_UNIT.KL },
      ];
    }

    this.setValues();
  }

  get isObsTemp() : boolean {
    return this._isObsTemp;
  }

  @Output() unitChange: EventEmitter<any> = new EventEmitter();
  @Output() changeBlocked: EventEmitter<any> = new EventEmitter();

  unitOptions = [
    { value: QUANTITY_UNIT.MT },
    { value: QUANTITY_UNIT.BBLS },
    { value: QUANTITY_UNIT.KL },
  ];

  private _isObsTemp = false;

  constructor() { }

  // Component lifecycle hook on initialization of component
  ngOnInit(): void {
    this.setValues();
  }

  /**
   * Set values of unit dropdown
   *
   * @memberof UnitDropdownComponent
   */
  setValues() {
    const unit = localStorage.getItem('unit');
    const unitObs = localStorage.getItem('unitObs');
    if (this.isObsTemp) {
      if (unitObs) {
        this.setSelected(unitObs);
        this.unitChange.emit({ unitObs: this.selectedUnit.value })
      } else {
        this.setSelected(AppConfigurationService.settings.obqDefaultWeightUnit);
        this.setUnit(true);
        this.unitChange.emit({ unitObs: this.selectedUnit.value })
      }
    } else {
      if (unit) {
        this.setSelected(unit)
        this.unitChange.emit({ unit: this.selectedUnit.value })
      } else {
        this.setSelected(AppConfigurationService.settings.defaultWeightUnit);
        this.setUnit();
      }
    }
  }

  // Method called on unit change
  onUnitChange(_event) {
    if (this.disableUnitChange) {
      this.setSelected(this.isObsTemp ? localStorage.getItem('unitObs') : localStorage.getItem('unit'));
      this.changeBlocked.next()
    } else {
      this.setUnit(this.isObsTemp);
      if (this.isObsTemp) {
        this.unitChange.emit({ unitObs: this.selectedUnit.value })
      } else {
        this.unitChange.emit({ unit: this.selectedUnit.value })
      }
    }
  }

  // Method to set the unit in the application
  setUnit(isObsTemp = false) {
    if (isObsTemp) {
      localStorage.setItem('unitObs', this.selectedUnit.value);
    } else {
      localStorage.setItem('unit', this.selectedUnit.value);
    }
  }

  // Method to set the selected unit
  setSelected(unit) {
    this.selectedUnit = { value: unit }
  }

}
