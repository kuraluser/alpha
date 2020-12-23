export const environment = {
  ENVOY_SERVER_PATH: '/mnt/efs/serverData',
  SPLIT_FILE_SIZE: 1000,
  PORT: 3000,
  DB_NAME: 'envoy_server',
  DB_USERNAME: 'postgres',
  DB_PASSWORD: 'envoyserver',
  DB_HOST: '18.221.103.183',
  DB_PORT: 5432,
  REDIS_SENTINAL_SERVERS: [{ host: '3.15.180.179', port: 26379 }],
  REDIS_MASTER_NAME: 'mymaster',
  REDIS_MASTER_PASSWORD: 'envoyserver',
  JWT_PUBLIC_KEY_PATH: '/mnt/efs/keys/public_keys/jwt'
};
