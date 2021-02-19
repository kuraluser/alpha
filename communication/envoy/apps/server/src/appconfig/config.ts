import { environment } from './environment';

/**
 * Configurtaion file for different enviroments
 */
export default () => ({
  appPath: environment.ENVOY_SERVER_PATH,
  splitFileSize: environment.SPLIT_FILE_SIZE,
  port: environment.PORT,
  dbHost: environment.DB_HOST,
  dbPort: environment.DB_PORT,
  dbUserName: environment.DB_USERNAME,
  dbPassword: environment.DB_PASSWORD,
  dbName: environment.DB_NAME,
  redisSentinalServers: environment.REDIS_SENTINAL_SERVERS,
  redisMasterName: environment.REDIS_MASTER_NAME,
  redisMasterPassword: environment.REDIS_MASTER_PASSWORD,
  jwtPublicKeyPath: environment.JWT_PUBLIC_KEY_PATH
  });