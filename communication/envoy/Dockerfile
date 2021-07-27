FROM node:12-alpine
ARG app
RUN apk add --update nginx && apk add openrc && rm -rf /tmp/* /var/cache/apk/*
COPY --chown=nginx nginx-conf/nginx-$app.conf /etc/nginx/nginx.conf
RUN rm -rf /etc/nginx/conf.d
RUN rc-update add nginx default
COPY $app/package.json .
COPY $app/yarn.lock .
RUN yarn install --frozen-lockfile
COPY $app .
CMD [ "node", "main.js" ]