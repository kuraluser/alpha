import { Controller, Get, Param, Res } from '@nestjs/common';
import { CommonDownloadApiService } from "@envoy/common";
import { ConfigService } from '@nestjs/config';

/**
 * Controller to upload files for communication
 */
@Controller()
export class DownloadController {

  constructor(private configService: ConfigService, private readonly commonDownloadApiService: CommonDownloadApiService) {
  }

  /**
   * Request method to download a single file
   * 
   * @param params 
   * @param file 
   */
  @Get('download/:clientId/:shipId')
  public downloadFile(@Param() params, @Res() res): void {
    this.commonDownloadApiService.dowloadFile(params, res);
  }
}
