# backend
![Travis Status](https://travis-ci.org/atiw-sportfest/backend.svg?branch=master-new)

Namespace: de.atiw.sportfest

## Endpoints

Method | URL
----------|------------------------
**POST**|`/authentication`
**GET**|`/klasse`
**GET**|`/klasse/{kid}`
**GET**|`/klasse/{kid}/anmeldung`
**POST**|`/klasse/anmeldung`
**PUT**|`/klasse`
**DELETE**|`/klasse/{kid}`
**GET**|`/ergebnis/{did}`
**POST**|`/ergebnis/{did}/{eid}`
**PUT**|`/ergebnis/{did}`
**DELETE**|`/ergebnis/{did}/{eid}`
**GET**|`/disziplin`
**GET**|`/disziplin/{did}`
**PUT**|`/disziplin`
**POST**|`/disziplin`
**POST**|`/disziplin/{did}`
**DELETE**|`/disziplin/{did}`
**GET**|`/schueler`
**GET**|`/schueler/{sid}`
**GET**|`/schueler/klasse/{kid}`
**GET**|`/schueler`
**GET**|`/schueler/klasse/{kid}/disziplin/{did}`
**POST**|`/schueler/upload`
**PUT**|`/schueler`
**DELETE**|`/schueler/{sid}`
**GET**|`/user`
**GET**|`/user/privileges`
**POST**|`/user`
**POST**|`/user/password`
**POST**|`/user/login`
**DELETE**|`/user/{uid}`
**GET**|`/ergebnis`
**GET**|`/ergebnis/{eid}`
**POST**|`/ergebnis`
**PUT**|`/ergebnis`
**DELETE**|`/ergebnis/{eid}`
**GET**|`/leistung`
**GET**|`/leistung/{lid}`
**POST**|`/leistung`
**PUT**|`/leistung/{isTeamleistung}`
**DELETE**|`/leistung/{lid}`



&nbsp;|&nbsp;
---|---
`{uid}`| User ID   
`{kid}`| Klassen ID
`{did}`| Disziplin ID
`{eid}`| Ergebnis ID 

## Compile

Git Bash: `./gradlew war`

`backend.war` ist dann in `build/libs`.

## Deploy to Local Tomcat

Git Bash (User und Passwort ersetzen):

    export CARGO_USER=user CARGO_PASS=pass CARGO_HOST=localhost CARGO_CONTAINER=tomcat8x
    ./gradlew redeployWar

## MySQL Database connection

Download MySQL Driver and put into `$CATALINA_BASE/lib`: [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).

Add this to `$CATALINA_BASE/conf/context.xml` (replace `USER`, `PASSWORD`, `HOST` and `DATABASE`):

    <Resource name="jdbc/sportfest" auth="Container" type="javax.sql.DataSource"
        maxTotal="100" maxIdle="30" maxWaitMillis="10000"
        username="USER" password="PASSWORD" driverClassName="com.mysql.jdbc.Driver"
        url="jdbc:mysql://HOST:3306/DATABASE"/>

See also [Tomcat JNDI Resources How-To](https://tomcat.apache.org/tomcat-8.0-doc/jndi-resources-howto.html#JDBC_Data_Sources), [JNDI Resources Examples (MySQL)](https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html#MySQL_DBCP_Example).

## Get Insomnia Workspace

Insomnia -> Import/Export -> Import from URL -> https://github.com/atiw-sportfest/backend/raw/master-new/meta/Insomnia.json
