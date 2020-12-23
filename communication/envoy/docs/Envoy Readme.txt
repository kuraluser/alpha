
ENVOY APPLICATION MAIN SYSTEMS

Client
 inbound data    -  Updated by API
 outboud data    -  Recieved from Socket(Server)
Server
 inbound data    -  Updated by API
 outboud data    -  Recieved from Socket(Client)
 
Inbound Processes and status 
 Process                        Process Status
  upload                         UPLOAD_WITH_HASH_VERIFIED
  split                          SPLIT_SUCCESS | SPLIT_FAILED
  send                           SEND_SUCCESS | SEND_FAILED
  finish                         FINISH_SUCCESS | FINISH_FAILED | FINISH_ERROR
  confirm                        CONFIRM_SUCCESS | CONFIRM_FAILED | CONFIRM_ERROR
  
Outbound Processes and status 
 Process                        Process Status
  received                       RECEIVED_SUCCESS | RECEIVED_WITH_PACKET_MISSING
  verified                       RECEIVED_WITH_HASH_VERIFIED | RECEIVED_WITH_HASH_FAILED
  shared                         
  
 sudo mount -t efs -o tls fs-a026a7d8:/ efs
  
 yarn install --frozen-lockfile
 
 Client
 build- nx build client --configuration=production
 windows run-   set APP_ENV=production && set APP_TYPE=ship && node main.js
 linux run- export APP_ENV=production && export APP_TYPE=ship && node main.js
 
 Server
 build- nx build server --configuration=production
 windows run-   set APP_ENV=production && set APP_TYPE=shore && node main.js
 linux run-   export APP_ENV=production && export APP_TYPE=shore && node main.js
 
 -For packaging install
  npm install pkg -g
  
 packaging from dist folder
   pkg . --targets node12-win-x64
    
 Run application after creating package
   
 set APP_ENV=production && set APP_TYPE=ship && envoy-client
 
 export APP_ENV=test && export APP_TYPE=shore
 #nohup node /home/ec2-user/envoy-server/server/main.js 1> log.out 2> err.out &
 
 Run Server application using pm2
 npm install pm2
 export APP_ENV=test && export APP_TYPE=shore
 pm2 start /home/ec2-user/envoy-server/server/main.js
 
 