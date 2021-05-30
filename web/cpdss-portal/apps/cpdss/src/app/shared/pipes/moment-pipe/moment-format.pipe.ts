import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'momentFormat'
})
export class MomentFormatPipe implements PipeTransform {

  transform(value: Date | moment.Moment, momentFormat: string): any {
    return moment(value).format(momentFormat);
  }

}
