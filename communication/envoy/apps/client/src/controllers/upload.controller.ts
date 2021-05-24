import { Controller, Post, Param, Headers, Req, Put } from '@nestjs/common';
import { CommonUploadApiService } from "@envoy/common";
import { InboundEventUploadResponse } from '@envoy/common';
import { ConfigService } from '@nestjs/config';

/**
 * Controller to upload files for communication
 */
@Controller()
export class UploadController {

  constructor(private configService: ConfigService, private readonly commonApiService: CommonUploadApiService) {
  }

  /**
   * Request method to upload a single file
   * 
   * @param params 
   * @param file 
   */
  @Post('push/:clientId/:messageType/:messageId/:seq')
  public async pushFile(@Param() params, @Req() req, @Headers('checksum') checksum, @Headers('algo') algo, @Headers('split') split): Promise<InboundEventUploadResponse> {
    params.shipId = this.configService.get<string>('shipId');
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
  @Put('push/:clientId/:messageId')
  public async updateFile(@Param() params, @Req() req, @Headers('checksum') checksum, @Headers('algo') algo, @Headers('split') split): Promise<InboundEventUploadResponse> {
    params.shipId = this.configService.get<string>('shipId');
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
}
