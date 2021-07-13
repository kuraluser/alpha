export const environment = {
  ENVOY_SERVER_PATH: `${process.env.CONTAINER_VOLUME_PATH}/server/serverData`,
  SPLIT_FILE_SIZE: 1000*20,
  PORT: `${process.env.PORT || 3000}`,
  DB_NAME: process.env.DB_NAME,
  DB_USERNAME: process.env.DB_USER,
  DB_PASSWORD: process.env.DB_PASSWORD,
  DB_HOST: process.env.DB_HOST,
  DB_PORT: 5432,
  REDIS_SENTINAL_SERVERS: [{ host: 'redis-stack_redis-sentinel', port: 26379 }],
  REDIS_MASTER_NAME: 'mymaster',
  REDIS_MASTER_PASSWORD: process.env.REDIS_PASSWORD,
  JWT_PUBLIC_KEY_PATH: `${process.env.CONTAINER_VOLUME_PATH}/server/jwt`
};
