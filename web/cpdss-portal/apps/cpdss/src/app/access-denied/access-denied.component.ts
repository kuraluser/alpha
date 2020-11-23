import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

/**
 * Compoent class for access denied nabvigation
 *
 * @export
 * @class AccessDeniedComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-access-denied',
  templateUrl: './access-denied.component.html',
  styleUrls: ['./access-denied.component.scss']
})
export class AccessDeniedComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  goBackToHome() {
    this.router.navigate(['/']);
  }

}
