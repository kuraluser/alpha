import * as winston from 'winston';
import * as DailyRotateFile from 'winston-daily-rotate-file';
import { EventEmitter } from 'events';

/**
 * Common constants
 */
export enum ProcessStatus {
  UPLOAD_WITH_HASH_VERIFIED,
  SPLIT_SUCCESS,
  SPLIT_FAILED,
  SEND_SUCCESS,
  SEND_FAILED,
  FINISH_FAILED,
  FINISH_SUCCESS,
  FINISH_ERROR,
  CONFIRM_ERROR,
  CONFIRM_FAILED,
  CONFIRM_SUCCESS,
  RECEIVED_SUCCESS,
  RECEIVED_WITH_HASH_VERIFIED,
  RECEIVED_WITH_HASH_FAILED,
  RECEIVED_WITH_PACKET_MISSING,
  CANCELLED_SUCCESS
}

export class AppConstants {
  public static PART_NUMBER_ZERO = "0000";
}

export type ProcessStatusLevels = keyof typeof ProcessStatus;

export type TaskCompleted = (err: string, result: string) => void;

export const logEmitter = new EventEmitter();

//Data emitter for simulate a client call to upload the file
export const dataEmitter = new EventEmitter();

const logLevel = process.env.LOG_LEVEL?process.env.LOG_LEVEL:'debug';

// Configuring winston logger
const WinstonLogger = winston.createLogger({
  level: logLevel,
  format: winston.format.combine(
    winston.format.splat(),
    winston.format.simple()
  )
});
console.log("Process app env " + process.env.APP_ENV);
console.log("Process app type " + process.env.APP_TYPE);

WinstonLogger.add(new winston.transports.Console());

// // Log to console if its development
// if (!process.env.APP_ENV) {
//   WinstonLogger.add(new winston.transports.Console());
// } else {
//   // Log to rotating file if its for production or ship
//   let dailyRotateTransport: DailyRotateFile;
//   if (!process.env.APP_TYPE || process.env.APP_TYPE.trim() === 'ship') {
//     dailyRotateTransport = new DailyRotateFile({
//       frequency: '5m',
//       filename: 'envoy-%DATE%.log',
//       datePattern: 'YYYY-MM-DD-HHmm',
//       zippedArchive: true,
//       dirname: 'logs',
//       maxFiles: '6'
//     });
//     dailyRotateTransport.on('archive', (zipFilename) => {
//       console.log("here archived "+zipFilename);
//       //Emitting log file while archiving
//       logEmitter.emit('log', zipFilename);
//     });
//   } else {
//     dailyRotateTransport = new DailyRotateFile({
//       filename: 'envoy-%DATE%.log',
//       datePattern: 'YYYY-MM-DD-HH',
//       zippedArchive: true,
//       dirname: 'logs',
//       maxSize: '10m',
//       maxFiles: '14d'
//     });
//   }
//   WinstonLogger.configure({
//     level: logLevel,
//     transports: [
//       dailyRotateTransport
//     ]
//   });
// }

export { WinstonLogger };