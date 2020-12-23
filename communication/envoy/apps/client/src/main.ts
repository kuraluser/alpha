import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { ConfigService } from '@nestjs/config';

/**
 * Main entry point to the client application
 */
async function bootstrap() {
  process.on('uncaughtException', function (exception) {
    console.log(exception);
  });
  try {
    const app = await NestFactory.create(AppModule);
    const configService = app.get(ConfigService);
    const port = configService.get('port');
    await app.listen(port);
  } catch (err) {
    console.error(`Failed to initialize, due to ${err}`);
    process.exit(1);
  }
}
bootstrap();
