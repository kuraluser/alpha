export const environment = {
  ENVOY_SERVER_PATH: '../serverData',
  SPLIT_FILE_SIZE: 1000,
  PORT: 3000,
  DB_NAME: 'envoy_server',
  DB_USERNAME: 'postgres',
  DB_PASSWORD: 'postgres',
  DB_HOST: '127.0.0.1',
  DB_PORT: 5432,
  REDIS_SENTINAL_SERVERS: [{ host: '127.0.0.1', port: 26379 }],
  REDIS_MASTER_NAME: 'mymaster',
  REDIS_MASTER_PASSWORD: 'Admin@123',
  JWT_PUBLIC_KEY_PATH: `${__dirname}/keys/public_keys/jwt`
};