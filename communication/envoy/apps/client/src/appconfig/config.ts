/**
 * Configurtaion file for different enviroments
 */
import { environment } from './environment';

export default () => ({
  shipId: environment.SHIP_ID,
  appPath: environment.ENVOY_CLIENT_PATH,
  socketUrl: environment.SOCKET_URL,
  port: environment.PORT,
  splitFileSize: environment.SPLIT_FILE_SIZE,
  dbHost: environment.DB_HOST,
  dbPort: environment.DB_PORT,
  dbUserName: environment.DB_USERNAME,
  dbPassword: environment.DB_PASSWORD,
  dbName: environment.DB_NAME,
  enableClientCert: environment.ENABLE_CLIENT_CERT,
  verifyServerCert: environment.VERIFY_SERVER_CERT,
  serverCAFile: environment.SERVER_CA_FILE,
  clientCertFile: environment.CLIENT_CERT_FILE,
  clientKeyFile: environment.CLIENT_KEY_FILE,
  clientJWTKeyFile: environment.CLIENT_JWT_KEY_FILE
});