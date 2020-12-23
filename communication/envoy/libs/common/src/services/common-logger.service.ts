import { Injectable, Scope } from '@nestjs/common';
import { WinstonLogger } from '../utils/common-app.constants';
import { format } from 'date-and-time';


/**
 * Common logger service
 */
@Injectable({ scope: Scope.TRANSIENT })
export class CommonLoggerService  {

    private context: string;

    setContext(context: string){
        this.context = context;
    }

    log(message: string) {
        const time = format(new Date(), 'YYYY-MM-DD hh:mm:ss A [GMT]Z', true);
        WinstonLogger.log('info', `%s [%s] ${message} `, time, this.context);
    }
    error(message: string, stacktrace: string) {
        const time = format(new Date(), 'YYYY-MM-DD hh:mm:ss A [GMT]Z', true);
        WinstonLogger.log('error', `%s [%s] ${message} \n [%s]`, time, this.context, stacktrace);
    }
    warn(message: string) {
        const time = format(new Date(), 'YYYY-MM-DD hh:mm:ss A [GMT]Z', true);
        WinstonLogger.log('warn', `%s [%s] ${message} `, time, this.context);
    }
    debug(message: string) {
        const time = format(new Date(), 'YYYY-MM-DD hh:mm:ss A [GMT]Z', true);
        WinstonLogger.log('debug', `%s [%s] ${message} `, time, this.context);
    }
    verbose(message: string) {
        const time = format(new Date(), 'YYYY-MM-DD hh:mm:ss A [GMT]Z', true);
        WinstonLogger.log('verbose', `%s [%s] ${message} `, time, this.context);
    }
}