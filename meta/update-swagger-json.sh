#!/usr/bin/env bash
git show master-new:meta/swagger.json > swagger.json
git add swagger.json
git commit -m "Update swagger.json"
