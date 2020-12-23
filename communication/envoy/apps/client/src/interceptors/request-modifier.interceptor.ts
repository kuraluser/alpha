
import { Injectable, NestMiddleware, Inject } from '@nestjs/common';
import { Request, Response } from 'express';
import { ConfigService } from '@nestjs/config';


/**
 * Request Modifier interceptor
 */
@Injectable()
export class RequestModfierInterceptor implements NestMiddleware {

    constructor(private configService: ConfigService) { }

    use(req: Request, res: Response, next: Function) {
        req.prefixFilePath = this.configService.get<string>('appPath');
        next();
    }
}
