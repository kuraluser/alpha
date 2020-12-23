export const environment = {
    SHIP_ID: `${process.env.SHIP_ID || '123456asdef'}`,
    ENVOY_CLIENT_PATH: '../clientData',
    SOCKET_URL: `https://test-cpdss-comm.alphaorimarine.com:443/id-${process.env.SHIP_ID || '123456asdef'}`,
    SPLIT_FILE_SIZE: 1000,
    PORT: 3001,
    DB_NAME: 'envoy_client',
    DB_USERNAME: 'postgres',
    DB_PASSWORD: 'postgres',
    DB_HOST: '127.0.0.1',
    DB_PORT: 5432,
    VERIFY_SERVER_CERT: true,
    ENABLE_CLIENT_CERT: true,
    SERVER_CA_FILE: `${__dirname}/keys/server_ca/prod-ca.pem`,
    CLIENT_CERT_FILE: `${__dirname}/keys/client_certificates/tls/client-cert.pem`,
    CLIENT_KEY_FILE: `${__dirname}/keys/client_certificates/tls/client-key.pem`,
    CLIENT_JWT_KEY_FILE: `${__dirname}/keys/client_certificates/jwt/private_key.pem`
  };
