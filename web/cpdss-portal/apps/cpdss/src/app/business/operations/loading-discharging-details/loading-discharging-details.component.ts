import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'cpdss-portal-loading-discharging-details',
  templateUrl: './loading-discharging-details.component.html',
  styleUrls: ['./loading-discharging-details.component.scss']
})
export class LoadingDischargingDetailsComponent implements OnInit {

  formGroup: FormGroup;

  constructor(private fb:FormBuilder) { }

  ngOnInit(): void {
    this.formGroup = this.fb.group({
      loadingRatio: ''
    })
  }

  clearFilter(dd){
  }

  onSubmit(){}

  change(){

  }

}
