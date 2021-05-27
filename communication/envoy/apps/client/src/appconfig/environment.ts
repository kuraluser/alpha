export const environment = {
    SHIP_ID: `${process.env.SHIP_ID || '9513402'}`,
    ENVOY_CLIENT_PATH: '../clientData',
    SOCKET_URL: `http://192.168.2.89:4800/id-${process.env.SHIP_ID || '9513402'}`,
    SPLIT_FILE_SIZE: 1000*20,
    PORT: `${process.env.PORT || 3001}`,
    DB_NAME: 'envoy_client',
    DB_USERNAME: 'postgres',
    DB_PASSWORD: 'Think@123',
    DB_HOST: '192.168.1.178',
    DB_PORT: 5432,
    VERIFY_SERVER_CERT: true,
    ENABLE_CLIENT_CERT: true,
    SERVER_CA_FILE: `${__dirname}/keys/server_ca/ca.pem`,
    CLIENT_CERT_FILE: `${__dirname}/keys/client_certificates/tls/client-cert.pem`,
    CLIENT_KEY_FILE: `${__dirname}/keys/client_certificates/tls/client-key.pem`,
    CLIENT_JWT_KEY_FILE: `${__dirname}/keys/client_certificates/jwt/private_key.pem`
  };