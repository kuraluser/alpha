export const environment = {
  ENVOY_SERVER_PATH: '../serverData',
  SPLIT_FILE_SIZE: 1000*20,
  PORT: `${process.env.PORT || 3000}`,
  DB_NAME: 'envoy_server',
  DB_USERNAME: 'postgres',
  DB_PASSWORD: 'Think@123',
  DB_HOST: '192.168.1.178',
  DB_PORT: 5432,
  REDIS_SENTINAL_SERVERS: [{ host: '127.0.0.1', port: 26379 }],
  REDIS_MASTER_NAME: 'mymaster',
  REDIS_MASTER_PASSWORD: 'pass',
  JWT_PUBLIC_KEY_PATH: `${__dirname}/keys/public_keys/jwt`
};