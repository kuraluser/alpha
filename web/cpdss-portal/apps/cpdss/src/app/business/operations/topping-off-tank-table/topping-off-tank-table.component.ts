import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargo } from '../../../shared/models/common.model';
import { IShipCargoTank } from '../../core/models/common.model';
import { IToppingOffSequence } from '../models/loading-information.model';
import { ToppingOffTankTableApiService } from './topping-off-tank-table-api.service';
import { IToppingOffSequenceValueObject } from './topping-off-tank-table-transformation.model';
import { ToppingOffTankTableTransformationService } from './topping-off-tank-table-transformation.service';
import { IToppingoffUllageRequest } from './topping-off-tank-table.model';

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
  @Input() voyageId: number;
  @Input() vesselId: number;
  @Input() portRotationId: number;
  @Input() cargos: ICargo[];
  @Input() toppingOffSequence: IToppingOffSequence[];
  @Input() cargoTanks: IShipCargoTank[][];

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
    private toppingOffTankTableTransformationService: ToppingOffTankTableTransformationService,
    private toppingOffTankTableApiService: ToppingOffTankTableApiService) { }

  ngOnInit(): void {
    this.toppingOffSequenceFormArray = this.fb.group({
      toppingOffSequence: this.fb.array([])
    });
    this.cargoTypeColumns = this.toppingOffTankTableTransformationService.getDatatableColumns();
    this.toppingOffSequence.map((topping) => {
      const cargo = this.findCargo(topping.cargoId);
      const foundTank = this.findTank(topping?.tankId)
      topping.cargoName = cargo?.name;
      topping.cargoAbbreviation = cargo?.abbreviation;
      topping.shortName = foundTank?.shortName;
      topping.colourCode = foundTank?.commodity?.colorCode;
      return topping;
    })
    this.toppingOffSequence.sort((a, b) => (a.displayOrder > b.displayOrder) ? 1 : ((b.displayOrder > a.displayOrder) ? -1 : 0))
    this.initiToppingSequenceArray();


  }

  /**
* Method to find out cargo
*
* @memberof ToppingOffTankTableComponent
*/
  findCargo(cargoId): ICargo {
    let cargoDetail;
    this.cargos?.map((cargo) => {
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
      fillingRatio: this.fb.control(toppingOffSequence.fillingRatio, [Validators.required]),
      api: this.fb.control(toppingOffSequence.api, [Validators.required]),
      temperature: this.fb.control(toppingOffSequence.temperature, [Validators.required]),
      displayOrder: this.fb.control(toppingOffSequence.displayOrder, [Validators.required])
    })
  }

  /**
* Event handler for row re order complete event
*
* @memberof ToppingOffTankTableComponent
*/
  onRowReorder(event, groupIndex) {
    for (let i = 0; i < this.cargoTypeList[groupIndex].length; i++) {
      this.cargoTypeList[groupIndex][i].displayOrder = i + 1;
    }
    this.toppingOffSequenceFormArray = this.fb.group({
      toppingOffSequence: this.fb.array([])
    });
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
    const toppingOffSequenceList = this.toppingOffTankTableTransformationService.getToppingOffSequenceAsValue(this.cargoTypeList)
    this.updateToppingOff.emit(toppingOffSequenceList);

  }

  findTank(tankId) {
    const tankGroup = this.cargoTanks.find((groupTank) => {
      let found = groupTank.find((tank) => tank.id === tankId)
      return found;
    })
    const tank = tankGroup.find((tank) => tank.id === tankId)
    return tank;
  }

  /**
 * Event handler for edit complete event
 *
 * @memberof PortsComponent
 */
  async onEditComplete(event, groupIndex) {
    if (event.field === "ullage") {
      let ullageData = <IToppingoffUllageRequest>{}
      ullageData.id = event.data.id;
      ullageData.correctedUllage = event.data.ullage.value;
      ullageData.tankId = event.data.tankId;
      ullageData.api = event.data.api;
      ullageData.temperature = event.data.temperature;
      ullageData.sg = '';
      ullageData.isBallast = false;
      ullageData.isCommingle = false;
      const result = await this.toppingOffTankTableApiService.updateUllage(this.vesselId, this.voyageId, this.portRotationId, ullageData).toPromise();
      if (result?.responseStatus?.status === '200') {
        this.cargoTypeList[groupIndex][event.index]['quantity'] = result.quantityMt;
        this.updateField(groupIndex, event.index, 'quantity', result.quantityMt);
        this.cargoTypeList[groupIndex][event.index]['fillingRatio'] = result.fillingRatio;
        this.updateField(groupIndex, event.index, 'fillingRatio', result.fillingRatio);
      }
    }
    const toppingOffSequenceList = this.toppingOffTankTableTransformationService.getToppingOffSequenceAsValue(this.cargoTypeList)
    this.updateToppingOff.emit(toppingOffSequenceList);
  }

  /**
* Method for updating form field
*
* @private
* @param {number} index
* @param {string} field
* @param {*} value
* @memberof PortsComponent
*/
  private updateField(groupIndex: number, index: number, field: string, value?: any) {
    const control = this.field(groupIndex, index, field);
    control.setValue(value);
    control.markAsDirty();
    control.markAsTouched();
  }

  /**
 * Method for fetching form fields
 *
 * @private
 * @param {number} formGroupIndex
 * @param {string} formControlName
 * @returns {FormControl}
 * @memberof PortsComponent
 */
  private field(groupIndex: number, formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.toppingOffSequenceForm.controls[groupIndex].get('dataTable')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

}
