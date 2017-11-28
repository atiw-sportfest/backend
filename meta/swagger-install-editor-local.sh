#!/usr/bin/env bash

. $(dirname $0)/.ask_tomcat.sh # $TOMCAT

target="$TOMCAT/webapps/swagger-editor"

[ -d "$target" ] || mkdir -p "$target"

curl -L https://github.com/swagger-api/swagger-editor/archive/master.tar.gz |  tar -xz -C "$target" swagger-editor-master/{index.html,dist} --strip-components=1
