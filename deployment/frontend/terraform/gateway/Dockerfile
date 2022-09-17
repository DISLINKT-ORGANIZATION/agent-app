FROM node:alpine3.11 as build

ARG API_URL="'http://localhost:8090/'"
ARG FRONTEND_APP_SRC_URL=https://github.com/DISLINKT-ORGANIZATION/agent-fe/archive/refs/tags/v0.5.0.tar.gz
	
WORKDIR /usr/src/server
RUN apk --update --no-cache add curl tar && \
    curl -L "${FRONTEND_APP_SRC_URL}" | tar -xz && \
	mv "$(find . -maxdepth 1 -type d | tail -n 1)" app && \
	cd app && \
    npm install && \
	echo "$API_URL" && \
	sed -i -e "s~.*~VUE_APP_API_URL=${API_URL}~g" .env && \	
	head -1 .env && \
	npm install @vue/cli@4.5.14 -g && \
    npm run build --production && \
	ls -a && \
    mv $(ls -d */|head -n 1) /app && \
	ls -a && \
	cd /app && \
	ls -a

FROM nginx:1.21-alpine as execute
COPY ./files/nginx.conf /etc/nginx/nginx.conf
COPY ./files/api_gateway.conf /etc/nginx/api_gateway.conf
COPY --from=build /app /usr/share/nginx/html/app
RUN cd /usr/share/nginx/html/app && \
	ls -a && \
	echo "$API_URL"
CMD sed -i \
        -e 's~PORT~'$PORT'~g' \
        -e 's~API_URL~'$API_URL'~g' \
        /etc/nginx/api_gateway.conf && \
    nginx -g 'daemon off;'
	