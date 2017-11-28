#!/usr/bin/env bash

[ -z "$TOMCAT" ] && read -ep "Tomcat folder (tab-completition enabled): " TOMCAT

[ -z "$TOMCAT" ] && { echo >&2 "Abort."; exit; }
export TOMCAT
