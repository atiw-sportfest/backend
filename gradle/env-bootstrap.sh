#!/usr/bin/env bash

echo >&2 "Setting up your environment..."

read -p "Tomcat Username: " CARGO_USER
read -p "Tomcat Password: " CARGO_PASS

cat >>$HOME/.bashrc <<EOF

# Config for Cargo
export CARGO_HOST=localhost CARGO_USER=$CARGO_USER CARGO_PASS=$CARGO_PASS CARGO_CONTAINER=tomcat8x
EOF

echo >&2 "Added config to your $HOME/.bashrc"
echo >&2 "Please execute the following to active the config for this session:"
echo >&2
echo >&2 " source ~/.bashrc"
echo >&2
echo >&2 "This is only needed once, every session you start afterwards will include the params by default."
echo >&2
echo >&2 "ATTENTION Please make sure your user has the \"manager-script\"-role! If not, add it to your user and restart tomcat."
echo >&2
