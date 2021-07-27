FROM node:12-alpine3.14
ARG app
RUN apk add --update supervisor nginx && rm -rf /tmp/* /var/cache/apk/*
COPY --chown=nginx nginx-conf/nginx-$app.conf /etc/nginx/nginx.conf
RUN rm -rf /etc/nginx/conf.d
COPY supervisord.conf /etc/supervisord.conf
COPY $app/package.json .
COPY $app/yarn.lock .
RUN yarn install --frozen-lockfile
COPY $app .
CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]