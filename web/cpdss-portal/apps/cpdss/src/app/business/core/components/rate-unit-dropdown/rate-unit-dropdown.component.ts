import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { RATE_UNIT } from '../../../../shared/models/common.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';

/**
 * Component class for unit dropdown for rate
 *
 * @export
 * @class RateUnitDropdownComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-rate-unit-dropdown',
  templateUrl: './rate-unit-dropdown.component.html',
  styleUrls: ['./rate-unit-dropdown.component.scss']
})
export class RateUnitDropdownComponent implements OnInit {

  @Input() showLabel = true;

  @Input() disableUnitChange = false;

  @Input() selectedUnit: { value: string };

  @Output() unitChange: EventEmitter<any> = new EventEmitter();

  @Output() changeBlocked: EventEmitter<any> = new EventEmitter();

  unitOptions = [
    { value: RATE_UNIT.M3_PER_HR },
    { value: RATE_UNIT.BBLS_PER_HR }
  ];

  constructor() { }

  ngOnInit(): void {
    const unit = localStorage.getItem('rate_unit');
    if (unit) {
      this.setSelected(unit)
      this.unitChange.emit({ unit: this.selectedUnit.value });
    } else {
      this.setSelected(AppConfigurationService.settings.defaultWeightUnit);
      this.setUnit();
    }
  }

  /**
   * Method called on unit change
   *
   * @param {*} _event
   * @memberof RateUnitDropdownComponent
   */
  onUnitChange(_event) {
    if (this.disableUnitChange) {
      this.setSelected(localStorage.getItem('rate_unit'));
      this.changeBlocked.next()
    } else {
      this.setUnit();
      this.unitChange.emit({ unit: this.selectedUnit.value })
    }
  }

  /**
   * Method to set the unit in the application
   *
   * @memberof RateUnitDropdownComponent
   */
  setUnit() {
    localStorage.setItem('rate_unit', this.selectedUnit.value)
  }

  /**
   * Method to set the selected unit
   *
   * @param {*} unit
   * @memberof RateUnitDropdownComponent
   */
  setSelected(unit) {
    this.selectedUnit = { value: unit }
  }

}
