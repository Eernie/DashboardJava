# docker.webpack

FROM ubuntu:latest

WORKDIR /app
COPY . /app

RUN apt-get update && apt-get install curl -y
RUN curl -sL https://deb.nodesource.com/setup_6.x | bash - && apt-get install nodejs -y
RUN npm install
CMD npm run serve:prod
