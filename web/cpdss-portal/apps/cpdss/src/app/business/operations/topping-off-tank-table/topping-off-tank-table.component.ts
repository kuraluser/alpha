import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargo } from '../../../shared/models/common.model';
import { IToppingOffSequence } from '../models/loading-information.model';
import { IToppingOffSequenceValueObject } from './topping-off-tank-table-transformation.model';
import { ToppingOffTankTableTransformationService } from './topping-off-tank-table-transformation.service';

/**
 * Component class for loading topping off tank component
 *
 * @export
 * @class ToppingOffTankTableComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-topping-off-tank-table',
  templateUrl: './topping-off-tank-table.component.html',
  styleUrls: ['./topping-off-tank-table.component.scss']
})
export class ToppingOffTankTableComponent implements OnInit {
  @Input() cargos: ICargo[];
  @Input() toppingOffSequence: IToppingOffSequence[];

  @Output() updateToppingOff: EventEmitter<IToppingOffSequence[]> = new EventEmitter();

  cargoTypeColumns: IDataTableColumn[] = [];
  cargoTypeList: any = [];
  toppingOffSequenceLists: IToppingOffSequenceValueObject[];
  editMode: DATATABLE_EDITMODE = DATATABLE_EDITMODE.CELL;
  toppingOffSequenceFormArray: FormGroup;


  get toppingOffSequenceForm(): FormArray {
    return this.toppingOffSequenceFormArray.get("toppingOffSequence") as FormArray
  }

  constructor(
    private fb: FormBuilder,
    private toppingOffTankTableTransformationService: ToppingOffTankTableTransformationService) { }

  ngOnInit(): void {
    this.toppingOffSequenceFormArray = this.fb.group({
      toppingOffSequence: this.fb.array([])
    });
    this.cargoTypeColumns = this.toppingOffTankTableTransformationService.getDatatableColumns();
    this.toppingOffSequence.map((topping) => {
      const cargo = this.findCargo(topping.cargoId);
      topping.cargoName = cargo?.name;
      topping.cargoAbbreviation = cargo?.abbreviation;
      return topping;
    })
    this.initiToppingSequenceArray();


  }

  /**
* Method to find out cargo
*
* @memberof ToppingOffTankTableComponent
*/
  findCargo(cargoId): ICargo {
    let cargoDetail;
    this.cargos.map((cargo) => {
      if (cargo.id === cargoId) {
        cargoDetail = cargo;
      }
    })
    return cargoDetail;
  }
  /**
* Method for init loading sequence array
*
* @memberof ToppingOffTankTableComponent
*/
  initiToppingSequenceArray() {
    const _toppingOffSequence = this.toppingOffSequence?.map((loadingDelay) => {
      const toppingOffSequenceData = this.toppingOffTankTableTransformationService.getToppingOffSequenceAsValueObject(loadingDelay, false, true);
      return toppingOffSequenceData;
    });

    this.cargoTypeList = Object.values(_toppingOffSequence.reduce((acc, obj) => {
      const key = obj['cargoId'];
      if (!acc[key]) {
        acc[key] = [];
      }
      // Add object to list for given key's value
      acc[key].push(obj);
      return acc;
    }, {}));
    for (let cargoType of this.cargoTypeList) {
      const toppingArray = cargoType.map((topping, index) =>
        this.initToppingOffSequenceFormGroup(topping, index)
      );
      const toppingOffSequenceForm = this.fb.group({
        dataTable: this.fb.array([...toppingArray])
      });
      const toppingOffSequenceArray = this.toppingOffSequenceFormArray.get('toppingOffSequence') as FormArray;
      toppingOffSequenceArray.push(toppingOffSequenceForm);
    }
  }

  /**
  * Method for initializing loading sequence row
  *
  * @private
  * @param {IToppingOffSequenceValueObject} toppingOffSequence
  * @returns
  * @memberof ToppingOffTankTableComponent
  */
  initToppingOffSequenceFormGroup(toppingOffSequence: IToppingOffSequenceValueObject, index: number) {
    return this.fb.group({
      id: this.fb.control(toppingOffSequence.id, [Validators.required]),
      loadingInfoId: this.fb.control(toppingOffSequence.loadingInfoId, [Validators.required]),
      orderNumber: this.fb.control(toppingOffSequence.orderNumber, [Validators.required]),
      tankId: this.fb.control(toppingOffSequence.tankId, [Validators.required]),
      cargoId: this.fb.control(toppingOffSequence.cargoId, [Validators.required]),
      shortName: this.fb.control(toppingOffSequence.shortName, [Validators.required]),
      cargoName: this.fb.control(toppingOffSequence.cargoName, [Validators.required]),
      cargoAbbreviation: this.fb.control(toppingOffSequence.cargoAbbreviation, [Validators.required]),
      colourCode: this.fb.control(toppingOffSequence.colourCode, [Validators.required]),
      remark: this.fb.control(toppingOffSequence.remark.value, [Validators.required]),
      ullage: this.fb.control(toppingOffSequence.ullage.value, [Validators.required]),
      quantity: this.fb.control(toppingOffSequence.quantity, [Validators.required]),
      fillingRatio: this.fb.control(toppingOffSequence.fillingRatio, [Validators.required])
    })
  }

  /**
* Event handler for row re order complete event
*
* @memberof ToppingOffTankTableComponent
*/
  onRowReorder(event, index) {
  }

    /**
   * Event handler for edit complete event
   *
   * @memberof PortsComponent
   */
     async onEditComplete(event) {
     
       const toppingOffSequenceList = this.toppingOffTankTableTransformationService.getToppingOffSequenceAsValue(this.cargoTypeList)
       this.updateToppingOff.emit(toppingOffSequenceList);
     }
}
