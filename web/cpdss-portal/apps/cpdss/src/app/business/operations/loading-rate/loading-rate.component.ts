import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'cpdss-portal-loading-rate',
  templateUrl: './loading-rate.component.html',
  styleUrls: ['./loading-rate.component.scss']
})

/**
 * Component class for loading / discharging rate component
 *
 * @export
 * @class LoadingRateComponent
 * @implements {OnInit}
 */
export class LoadingRateComponent implements OnInit {

  formGroup: FormGroup;
  constructor(
    private fb:FormBuilder
  ) { }

  ngOnInit(): void {
    this.formGroup = this.fb.group({
      maxLoadingrate: ''
    })
  }

  change(){
    
  }

}
