# Install Local Tomcat

- Download [Tomcat 8.5](https://tomcat.apache.org/download-80.cgi#8.5.15).
    - **Windows**: Wir brauchen die 32bit bzw. 64bit Windows zip (apache-tomcat-8.5.x-windows-xYY.zip).
- ZIP entpacken

In dem Tomcat Ordner muss dann in die `conf/tomcat-users.xml` ein Nutzer eingefügt werden. Dazu das folgende Tag innerhalb von `<tomcat-users></tomcat-users>` hinzufügen:

    <user username="tomcat" password="s3cret" roles="manager-gui,manager-script" />

Die Datei sollte dann in etwa wie folgt aussehen (hier ohne Kommentare).

    <?xml version="1.0" encoding="UTF-8"?>

    <tomcat-users xmlns="http://tomcat.apache.org/xml"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
        version="1.0">

        <user username="tomcat" password="s3cret" roles="manager-gui,manager-script" />

    </tomcat-users>

Tomcat noch nicht starten, es muss noch die Datenbank, wie im normalen Readme beschrieben, konfiguriert werden. Danach kann Tomcat dann gestartet werden.

# Datenbankverbindung einrichten

Für die Datenbankverbindung muss der MySQL Datenbanktreiber zum Tomcat hinzugefügt werden und die Verbindungsdaten angegeben werden.

- MySQL Driver [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) herunterladen
- Aus der ZIP wird nur die `mysql-connector-java-x.xx.xx-bin.jar` in den `lib`-Ordner im Tomcat-Verzeichnis kopieren

Add this to `$CATALINA_BASE/conf/context.xml` (replace `USER`, `PASSWORD`, `HOST` and `DATABASE`):

    <Resource name="jdbc/sportfest" auth="Container" type="javax.sql.DataSource"
        maxTotal="100" maxIdle="30" maxWaitMillis="10000"
        username="USER" password="PASSWORD" driverClassName="com.mysql.jdbc.Driver"
        url="jdbc:mysql://HOST:3306/DATABASE"/>

See also [Tomcat JNDI Resources How-To](https://tomcat.apache.org/tomcat-8.0-doc/jndi-resources-howto.html#JDBC_Data_Sources), [JNDI Resources Examples (MySQL)](https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html#MySQL_DBCP_Example).
