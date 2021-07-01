import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';


/**
 * Component class for add port pop up
 *
 * @export
 * @class AddPortPopupComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-add-port-popup',
  templateUrl: './add-port-popup.component.html',
  styleUrls: ['./add-port-popup.component.scss']
})
export class AddPortPopupComponent implements OnInit {
  @Input() display;
  @Output() displayPopUp = new EventEmitter<boolean>();
  addPortForm: FormGroup;
  ports = [];
  operations = [];
  afterPorts = [];
  beforePorts = [];
  constructor(private fb: FormBuilder) { }

  //TODO : Pending add port popup in operations
  ngOnInit(): void {
    this.addPortForm = this.fb.group({
      port: this.fb.control(null),
      operation: this.fb.control(null),
      afterPort: this.fb.control(null),
      beforePort: this.fb.control(null)
    });
  }

      /**
   * Save popup
   */
  save(){

  }

    /**
   * Cancel popup
   */
     cancel() {
      this.displayPopUp.emit(false);
    }
  

}
