import { Controller, Param, Req, Post, Put, Headers, HttpCode, Get } from '@nestjs/common';
import { InboundEventUploadResponse } from '@envoy/common';
import { CommonUploadApiService } from '@envoy/common';


/**
 * Controller to upload files for communication
 */
@Controller()
export class UploadAppController {
  constructor(private readonly commonApiService: CommonUploadApiService) { }


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
  @Post('push/:clientId/:shipId/:messageId/:seq')
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


}
