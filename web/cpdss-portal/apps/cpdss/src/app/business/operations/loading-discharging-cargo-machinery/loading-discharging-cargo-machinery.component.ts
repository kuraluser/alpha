import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ILoadingMachinesInUses, IMachineryInUses } from '../models/loading-information.model';

@Component({
  selector: 'cpdss-portal-loading-discharging-cargo-machinery',
  templateUrl: './loading-discharging-cargo-machinery.component.html',
  styleUrls: ['./loading-discharging-cargo-machinery.component.scss']
})

/**
 * Component class for loading discharging cargo machinery component
 *
 * @export
 * @class LoadingDischargingCargoMachineryComponent
 * @implements {OnInit}
 */
export class LoadingDischargingCargoMachineryComponent implements OnInit {
  @Input() machineryInUses: IMachineryInUses;
  @Output() updatemachineryInUses: EventEmitter<ILoadingMachinesInUses[]> = new EventEmitter();

  values: any = [];
  machinery: any = [];
  constructor() { }

  ngOnInit(): void {
    this.machineryInUses?.vesselPumps?.map((vesselpump) => {
      vesselpump.isUsing = this.machineryInUses?.loadingMachinesInUses?.some(loadingmachine => loadingmachine.pumpId === vesselpump.pumpTypeId);
    })
    this.machinery = this.machineryInUses?.vesselPumps?.reduce((acc, obj) => {
      const key = obj['pumpTypeId'];
      if (!acc[key]) {
        acc[key] = [];
      }
      // Add object to list for given key's value
      acc[key].push(obj);
      return acc;
    }, {});
    this.values = this.machineryInUses?.pumpTypes?.map((pump) => {
      return {
        machine: pump.name,
        columns: this.machinery[pump.id]
      }
    })
  }

  /**
* Method for changing capacity
*
* @memberof LoadingDischargingCargoMachineryComponent
*/
  onChange(column) {

  }

  /**
* Method for when using a machinary
*
* @memberof LoadingDischargingCargoMachineryComponent
*/
  onUse(column) {
    if (column.isUsing) {
      const machineInUse: ILoadingMachinesInUses = {
        id: 0,
        loadingInfoId: 43,
        pumpId: column.pumpTypeId,
        capacity: column.capacity,
        isUsing: column.isUsing
      }
      this.machineryInUses.loadingMachinesInUses.push(machineInUse)
    } else {
      this.machineryInUses.loadingMachinesInUses = this.machineryInUses?.loadingMachinesInUses?.filter((machineUse) => machineUse.pumpId !== column.pumpTypeId)
    }
    this.updatemachineryInUses.emit(this.machineryInUses.loadingMachinesInUses)
  }


}
