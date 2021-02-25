import { AbstractControl , FormControl } from '@angular/forms';

/**
 * Validator Function to check white space
 * @param control 
 */
export function whiteSpaceValidator(control: FormControl): { whitespace: boolean } {
  const isWhitespace = (control.value || '').trim().length === 0;
  const isValid = !isWhitespace;
  return isValid ? null : { 'whitespace': true };
}

