import { Controller, Param, Req, Post, Put, Headers, HttpCode, Get } from '@nestjs/common';
import { InboundEventUploadResponse } from '@envoy/common';
import { CommonUploadApiService } from '@envoy/common';
import { ServerUtilService } from '../utils/server-util.service';


/**
 * Controller to upload files for communication
 */
@Controller()
export class UploadAppController {
  constructor(private readonly commonApiService: CommonUploadApiService, private readonly serverUtilService: ServerUtilService) { }


  /**
   * Health check API
   */
  @Get('health')
  @HttpCode(204)
  public healthCheck() {
    return "OK";
  }



  /**
   * Request method to upload a single file
   * 
   * @param params 
   * @param req 
   * @param checksum 
   * @param algo 
   * @param split 
   */
  @Post('push/:clientId/:shipId/:messageType/:messageId/:seq')
  public async pushFile(@Param() params, @Req() req, @Headers('checksum') checksum, @Headers('algo') algo, @Headers('split') split): Promise<InboundEventUploadResponse> {
    return this.commonApiService.uploadFile(params, req, checksum, algo, split);
  }


  /**
   * Request method to update a single file
   * 
   * @param params 
   * @param req 
   * @param checksum 
   * @param algo 
   * @param split 
   */
  @Put('push/:clientId/:shipId/:messageId')
  public async updateFile(@Param() params, @Req() req, @Headers('checksum') checksum, @Headers('algo') algo, @Headers('split') split): Promise<InboundEventUploadResponse> {
    return this.commonApiService.updateFile(params, req, checksum, algo, split);
  }


   /**
   * Request method to cancel a single file
   * 
   * @param params 
   * @param req 
   * @param checksum 
   * @param algo 
   * @param split 
   */
  @Put('cancel/:clientId/:messageId/:shipId')
  public async cancelFile(@Param() params): Promise<InboundEventUploadResponse> {
    return this.commonApiService.cancelFile(params);
  }


  /**
   * Refresh public keys
   */
  @Get('refresh/publickeys')
  public async refreshPublicKeys() {
    return this.serverUtilService.refreshPublicKeys();
  }

}
