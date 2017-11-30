# backend
![Travis Status](https://travis-ci.org/atiw-sportfest/backend.svg?branch=master-new)

[Browse the Swagger/OpenAPI definition.][1]

This JAX-RS REST-API requires Apache TomEE 7.x or any other Java EE application server that uses CXF for JAX-RS, which is required for file upload.

It uses JPA to store it's data, you will need an default JPA data source and generate the initial schema.

See [README.INSTALL][5] for how to install TomEE on Windows.

## Assemble a WAR

Git Bash: `./gradlew war`

`backend.war` will appear in `build/libs`.

## Deploy to Local TomEE

Git Bash (Replace `user` and `pass` with your TomEE manager-app credentials):

    export CARGO_USER=user CARGO_PASS=pass CARGO_HOST=localhost CARGO_CONTAINER=tomcat8x
    ./gradlew redeployWar

Make sure that user has the `manager-script` role! For more information see [README.INSTALL][5]


## Get Insomnia Workspace

- Click the large Insomnia badge
- Import/Export
- Import from URL
    - https://github.com/atiw-sportfest/backend/raw/master-new/meta/Insomnia.json
    - or https://github.com/atiw-sportfest/backend/raw/master-new/meta/Insomnia-all-ciis.json

## Port to Other Application Server

You will need to change the types of the parameters of the methods that use multipart upload.

- [de.atiw.sportfest.backend.api.AnmeldungApi#anmeldungUploadPost](src/main/java/de/atiw/sportfest/backend/api/AnmeldungApi.java)

[1]: https://atiw-sportfest.github.io/backend/
[2]: https://dev.mysql.com/downloads/connector/j/
[3]: https://tomcat.apache.org/tomcat-8.0-doc/jndi-resources-howto.html#JDBC_Data_Sources
[4]: https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html#MySQL_DBCP_Example
[5]: README.INSTALL.md
