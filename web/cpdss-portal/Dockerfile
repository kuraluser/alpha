FROM nginx:1.18.0-alpine
ARG app
COPY --chown=nginx $app /usr/share/nginx/html
COPY --chown=nginx nginx/nginx.conf /etc/nginx/nginx.conf