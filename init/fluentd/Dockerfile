FROM 945995963191.dkr.ecr.ap-southeast-1.amazonaws.com/fluentd:base

## Install custom Fluentd plugins
RUN ["fluent-gem", "install", "fluent-plugin-elasticsearch", "--no-document", "--version", "4.3.3"]