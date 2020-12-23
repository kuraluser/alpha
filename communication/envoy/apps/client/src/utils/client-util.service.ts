import { Injectable } from '@nestjs/common';
import { promises as fsPromise } from 'fs';
import * as mkdirp from 'mkdirp';
import { CommonLoggerService } from '@envoy/common';

/**
 * Class to perform common util operations and return the results
 */
@Injectable()
export class ClientUtilService {


  constructor(private logger: CommonLoggerService) {
    this.logger.setContext('ClientUtilService');
  }

  /**
   * Writing data to the file
   *
   * @param filePath
   * @param data
   */
  public async writeToFile(directory: string, filePath: string, data: Buffer): Promise<string> {
    try {
      await mkdirp(directory);
      await fsPromise.writeFile(filePath, data);
      return Promise.resolve('success');
    } catch (err) {
      this.logger.error(`Error in writing to the file ${err.message}`, err.stack);
      return Promise.reject('error');
    }
  }


}
