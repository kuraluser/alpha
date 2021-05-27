import { Controller, Get, Param, Res } from '@nestjs/common';
import { CommonDownloadApiService, EventStatusResponse } from "@envoy/common";
import { ConfigService } from '@nestjs/config';

/**
 * Controller to upload files for communication
 */
@Controller()
export class DownloadController {

  constructor(private configService: ConfigService, private readonly commonDownloadApiService: CommonDownloadApiService) {
  }

  /**
   * Request method to download file based on message type
   * 
   * @param params 
   * @param file 
   */
  @Get('download/:clientId/:messageType/:shipId')
  public downloadFile(@Param() params, @Res() res): void {
    this.commonDownloadApiService.dowloadFile(params, res);
  }

  /**
   * Request method get status of a single file
   * 
   * @param params
   */
  @Get('status/:clientId/:messageId/:shipId')
  public getStatus(@Param() params): Promise<EventStatusResponse> {
    return this.commonDownloadApiService.getFileStatus(params);
  }
}
