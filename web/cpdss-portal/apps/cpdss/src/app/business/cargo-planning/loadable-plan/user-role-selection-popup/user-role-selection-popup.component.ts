import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'cpdss-portal-user-role-selection-popup',
  templateUrl: './user-role-selection-popup.component.html',
  styleUrls: ['./user-role-selection-popup.component.scss']
})
export class UserRoleSelectionPopupComponent implements OnInit {
@Input() visible: boolean;

  @Output() displayPopUp = new EventEmitter<any>();
  role = 'Master'
  constructor() { }

  ngOnInit(): void {
  }

  cancel(){
    this.displayPopUp.emit({visible: false});
  }

  submit(){
    this.displayPopUp.emit({visible: false, role: this.role});
  }
}
