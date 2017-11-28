#!/usr/bin/env bash

target=$1

[ -z "$target" ] && { echo >&2 "Missing target dir!"; exit 1; }

sj="$target/swagger.json"

[ -f "$sj" ] && read -p "Overwrite $sj? [yN]: " && [[ "${REPLY,,}" =~ ^y(es)?$ ]] || { echo "Abort."; exit; }

jq '.host = "localhost:8080" | .schemes = ["http"]' "$(dirname $0)/swagger.json" > "${target}/swagger.json"

echo >&2 Done.
