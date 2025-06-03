#!/bin/bash

TOMCAT_URL="http://3.86.140.185:8080/manager/html"
TOMCAT_USER="your-tomcat-user"
TOMCAT_PASS="your-tomcat-pass"
WAR_FILE="target/SimpleCustomerApp.war"

curl -u $TOMCAT_USER:$TOMCAT_PASS -T $WAR_FILE "$TOMCAT_URL/deploy?path=/SimpleCustomerApp&update=true"
