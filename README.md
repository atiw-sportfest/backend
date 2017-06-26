# backend
![Travis Status](https://travis-ci.org/atiw-sportfest/backend.svg?branch=master-new)

Namespace: de.atiw.sportfest

## Endpoints

Method | URL
----------|------------------------
**GET**|`/klasse`
**GET**|`/klasse/{kid}`
**PUT**|`/klasse`
**GET**|`/ergebnis/{did}`
**POST**|`/ergebnis/{did}/{eid}`
**PUT**|`/ergebnis/{did}`
**DELETE**|`/ergebnis/{did}/{eid}`
**GET**|`/disziplin`
**POST**|`/disziplin/{did}`
**PUT**|`/schueler`
**POST**|`/user/login`
**GET**|`/user/privileges`
**GET**|`/user`
**POST**|`/user`
**DELETE**|`/user/{uid}`

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
