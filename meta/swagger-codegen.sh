#!/usr/bin/env bash

genlang="jaxrs-spec"
ziptmp=$(mktemp)
dirtmp=$(mktemp -d)
tgtdir=$(cd .. && pwd)

if [ ! -z "$SWAGGER_CODEGEN_JAR" ]; then
    echo >&2 "Not yet supported."
    #java -jar "$SWAGGER_CODEGEN_JAR" generate -i swagger.yaml -l $genlang -o ../src/main/java -c swagger-codegen.config.json
else
    options=$(jq ".[0].spec = .[1] | .[0]" -s swagger-codegen.config.json swagger.json) # Embed spec in options
    response=$(curl -k https://generator.swagger.io/api/gen/servers/${genlang} -d "$options" -H "Content-Type: application/json")

    link=$(echo $response | jq ".link" -r)
    [ -z "$link" ] || [ "$link" == "null" ] && { echo >&2 $response; exit; }

    curl -L $link > $ziptmp

    pushd $dirtmp

    unzip $ziptmp

    pushd "${genlang}-server"

    rm pom.xml swagger.json

    cp -r * $tgtdir/src/main/java

    popd; popd

    rm -rf $dirtmp $ziptmp

    cd $tgtdir

    git add src/main/java
    git log swaggerimport.. -p src/main/java/de/atiw/sportfest/backend/{api,model} | git apply -3

    echo >&2 -e "\033[0;31mIf you want to abort, make sure you have cleared git rerere!\033[0;0m (Only if you use rerere.)"

fi
