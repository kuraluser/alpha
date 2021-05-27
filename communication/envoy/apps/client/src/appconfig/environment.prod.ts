export const environment = {
    SHIP_ID: `${process.env.SHIP_ID || '123456asdef'}`,
    ENVOY_CLIENT_PATH: `${process.env.CONTAINER_VOLUME_PATH}/client/clientData`,
    SOCKET_URL: `http://192.168.1.101:81/id-${process.env.SHIP_ID || '123456asdef'}`,
    SPLIT_FILE_SIZE: 1000*20,
    PORT: `${process.env.PORT || 3000}`,
    DB_NAME: 'envoy_client',
    DB_USERNAME: process.env.DB_USER,
    DB_PASSWORD: process.env.DB_PASSWORD,
    DB_HOST: process.env.DB_HOST,
    DB_PORT: 5432,
    VERIFY_SERVER_CERT: false,
    ENABLE_CLIENT_CERT: false,
    SERVER_CA_FILE: `${process.env.CONTAINER_VOLUME_PATH}/client/keys/server_ca/prod-ca.pem`,
    CLIENT_CERT_FILE: `${process.env.CONTAINER_VOLUME_PATH}/client/keys/client_certificates/tls/client-cert.pem`,
    CLIENT_KEY_FILE: `${process.env.CONTAINER_VOLUME_PATH}/client/keys/client_certificates/tls/client-key.pem`,
    CLIENT_JWT_KEY_FILE: `${__dirname}/keys/client_certificates/jwt/private_key.pem`
  };
