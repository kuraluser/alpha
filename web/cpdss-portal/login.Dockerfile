FROM nginx:1.18.0-alpine
ARG app
ARG doc
COPY --chown=nginx $app /usr/share/nginx/html/
COPY --chown=nginx $doc /usr/share/nginx/html/docs/
COPY --chown=nginx nginx/nginx-$app.conf /etc/nginx/nginx.conf
RUN rm -rf /etc/nginx/conf.d
RUN rm -rf /docker-entrypoint.d
RUN chmod -R 765 /usr/share/nginx/html/
RUN chmod -R 765 /etc/nginx/nginx.conf