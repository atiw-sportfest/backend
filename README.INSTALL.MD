# Install Local TomEE

TomEE WebProfile herunterladen: 7.0.4 [ZIP][1]/ [TGZ][2]

In dem TomEE Ordner muss dann in die `conf/tomcat-users.xml` ein Nutzer eingefügt werden. Dazu das folgende Tag innerhalb von `<tomcat-users></tomcat-users>` hinzufügen:

    <user username="tomcat" password="s3cret" roles="manager-gui,manager-script" />

Die Datei sollte dann in etwa wie folgt aussehen (hier ohne Kommentare).

    <?xml version="1.0" encoding="UTF-8"?>

    <tomcat-users xmlns="http://tomcat.apache.org/xml"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
        version="1.0">

        <user username="tomcat" password="s3cret" roles="manager-gui,manager-script" />

    </tomcat-users>

TomEE noch nicht starten, es muss noch die Datenbank, wie im normalen Readme beschrieben, konfiguriert werden. Danach kann TomEE dann gestartet werden.

# Datenbankverbindung einrichten

Für die Datenbankverbindung muss der MySQL Datenbanktreiber zum TomEE hinzugefügt werden und die Verbindungsdaten angegeben werden.

- MySQL Driver [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) herunterladen
- Aus der ZIP wird nur die `mysql-connector-java-x.xx.xx-bin.jar` in den `lib`-Ordner im Tomcat-Verzeichnis kopieren

Außerdem muss noch diese Resourcen in die `tomee/conf/tomee.xml` hinzugefügt werden.
Sie sollte im Anschluss dann wie folgt aussehen (`USER`, `PASSWORD`, `HOST` und `DATABASE` mit den entsprechenden Werten der Datenbank ersetzen):

    <?xml version="1.0" encoding="UTF-8"?>
    <tomee>

      <Resource id="jpaDS" type="javax.sql.DataSource">
          jdbcDriver = com.mysql.jdbc.Driver
          jdbcUrl = jdbc:mysql://HOST:3306/DATABASE
          userName = USER
          password = PASSWORD
      </Resource>

      <Resource id="jpaDS_unmanaged" type="javax.sql.DataSource">
          jdbcDriver = com.mysql.jdbc.Driver
          jdbcUrl = jdbc:mysql://HOST:3306/DATABASE
          userName = USER 
          password = PASSWORD
          jtaManaged = false
      </Resource>

    </tomee>

Im Anschluss müssen dann noch die folgenden Werte in die `conf/system.properties` hinzugefügt werden:

    javax.persistence.jtaDataSource = jpaDS
    javax.persistence.nonJtaDataSource = jpaDS_unmanaged
    # Die folgende Zeile solle nur im Entwicklungsbetrieb auskommentiert sein!
    #openjpa.jdbc.SynchronizeMappings=buildSchema(foreignKeys=true)

[1]: http://repo.maven.apache.org/maven2/org/apache/tomee/apache-tomee/7.0.4/apache-tomee-7.0.4-webprofile.zip
[2]: http://repo.maven.apache.org/maven2/org/apache/tomee/apache-tomee/7.0.4/apache-tomee-7.0.4-webprofile.tar.gz
