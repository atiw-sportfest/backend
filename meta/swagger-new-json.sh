#!/usr/bin/env bash
jq '.host = "sportfest.atiw.de" | .schemes = ["https"]' swagger_new.json > swagger.json
