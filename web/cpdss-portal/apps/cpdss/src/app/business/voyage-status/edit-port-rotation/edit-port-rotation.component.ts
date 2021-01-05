import { Component, EventEmitter, OnInit, Output } from '@angular/core';

/**
 * Component class of EditPortRotation
 *
 * @export
 * @class EditPortRotationComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-edit-port-rotation',
  templateUrl: './edit-port-rotation.component.html',
  styleUrls: ['./edit-port-rotation.component.scss']
})
export class EditPortRotationComponent implements OnInit {
  @Output() displayPopUp = new EventEmitter<boolean>();

  showPopUp: boolean;

  constructor() { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof EditPortRotationComponent
   */
  ngOnInit(): void {
    this.showPopUp = true;
  }
/**
 * Save edit port rotaion
 */
  saveEditPortRotation(){

  }

  /**
   * cancel popup
   */
  cancel(){
    this.displayPopUp.emit(false);
  }

}
