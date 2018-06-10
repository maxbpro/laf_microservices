#!/bin/sh
echo "********************************************************"
echo "Starting the Eureka Server"
echo "********************************************************"
java -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar /usr/local/eurekaserver/@project.build.finalName@.jar
