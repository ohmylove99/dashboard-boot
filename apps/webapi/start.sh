#!/bin/bash

echo "start applicaiton in production mode"

java -Xmx1024m -XX:MaxPermSize=128M -jar build/libs/webapi.war --spring.profiles.active=prod

#set the server port
#java -Xmx1024m -jar target/boot-api-5.0.0-SNAPSHOT.war --server.port=9090

#set the properties file location
#java -Xmx1024m -jar target/boot-api-5.0.0-SNAPSHOT.war --spring.config.location=/var/myapp/conf