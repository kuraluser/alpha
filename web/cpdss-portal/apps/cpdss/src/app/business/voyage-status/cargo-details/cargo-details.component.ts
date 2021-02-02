import { Component, Input, OnInit } from '@angular/core';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargoConditions, ICargoQuantities } from '../models/voyage-status.model';
import { VoyageStatusTransformationService } from '../services/voyage-status-transformation.service';
/**
 * Component class of CargoDetailsComponent
 */
@Component({
  selector: 'cpdss-portal-cargo-details',
  templateUrl: './cargo-details.component.html',
  styleUrls: ['./cargo-details.component.scss']
})
export class CargoDetailsComponent implements OnInit {
  @Input() cargoConditions: ICargoConditions[];
  @Input() cargoQuantities: ICargoQuantities[];
  columns: IDataTableColumn[];
  newCargoList: ICargoQuantities[] = [];
  totalDifference = 0;
  totalPlanned = 0;
  totalActual = 0;
  isTotalPositive = true;

  constructor(private voyageStatusTransformationService: VoyageStatusTransformationService) { }
  /**
   * Component lifecycle ngOnit
   *
   * @memberof CargoDetailsComponent
   */
  ngOnInit(): void {

    this.columns = this.voyageStatusTransformationService.getColumnFields();
    this.newCargoList = this.cargoConditions.map(itm => ({
      ...this.cargoQuantities.find((item) => item.cargoId === itm.id),
      ...itm
    }));
    this.newCargoList.map(cargoList => {
      this.totalPlanned = cargoList.plannedWeight + this.totalPlanned;
      const difference = cargoList.actualWeight - cargoList.plannedWeight;
      this.totalActual = cargoList.actualWeight + this.totalActual;
      cargoList.difference = difference;
      this.totalDifference = difference + this.totalDifference;
      difference > 0 ? cargoList.isPositive = true : cargoList.isPositive = false;
    });
    this.totalDifference > 0 ? this.isTotalPositive = true : this.isTotalPositive = false;

  }

}
