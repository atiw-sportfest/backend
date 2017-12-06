# Install Required Applications on Windows

0. You will need an Git Bash for this.
1. `git clone https://github.com/atiw-sportfest/tools.git` or download [`sportfest-setup.sh`][4] directly.
2. Review the `sportfest-setup.sh` (it's in `install` in the repo)
3. Execute it. This will install TomEE and MariaDB into `C:\sportfest`. (`C:\sportfest\tomee` resp. `C:\sportfest\mariadb`.)
4. Unfortunately you will still need to configure TomEE manually, so continue below.

## Configure Local TomEE

You will need to add an user to TomEE's `conf/tomcat-users.xml`. To do this, add the following in between `<tomcat-users></tomcat-users>`:

    <user username="tomcat" password="s3cret" roles="manager-gui,manager-script" />

The file should look like this afterwards: (comments ommitted)

    <?xml version="1.0" encoding="UTF-8"?>

    <tomcat-users xmlns="http://tomcat.apache.org/xml"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
        version="1.0">

        <user username="tomcat" password="s3cret" roles="manager-gui,manager-script" />

    </tomcat-users>

Do not start TomEE yet, wait for database configuration.

## Database Connection

You will need to configure the JDBC connection to MariaDB.

Make your TomEE's `conf/tomee.xml` look like the following (replace `USER`, `PASSWORD`, `HOST` and `DATABASE` with the corresponding credentials the setup gave you):

    <?xml version="1.0" encoding="UTF-8"?>
    <tomee>

      <Resource id="jpaDS" type="javax.sql.DataSource">
          jdbcDriver = org.mariadb.jdbc.Driver
          jdbcUrl = jdbc:mariadb://HOST:3306/DATABASE
          userName = USER
          password = PASSWORD
      </Resource>

      <Resource id="jpaDS_unmanaged" type="javax.sql.DataSource">
          jdbcDriver = org.mariadb.jdbc.Driver
          jdbcUrl = jdbc:mariadb://HOST:3306/DATABASE
          userName = USER 
          password = PASSWORD
          jtaManaged = false
      </Resource>

    </tomee>

Afterwards, add the follwing properties to TomEE's `conf/system.properties`.

    javax.persistence.jtaDataSource = jpaDS
    javax.persistence.nonJtaDataSource = jpaDS_unmanaged
    # Generate schemas. Not production-ready.
    openjpa.jdbc.SynchronizeMappings=buildSchema(foreignKeys=true)

## Starting the Applications

You now can start TomEE and MariaDB by launching `C:\sportfest\sportfest_run.bat`. (Open via Windows Explorer.)

[1]: http://repo.maven.apache.org/maven2/org/apache/tomee/apache-tomee/7.0.4/apache-tomee-7.0.4-webprofile.zip
[2]: http://repo.maven.apache.org/maven2/org/apache/tomee/apache-tomee/7.0.4/apache-tomee-7.0.4-webprofile.tar.gz
[3]: https://downloads.mariadb.org/connector-java/
[4]: https://github.com/atiw-sportfest/tools/raw/master/install/sporfest_setup.sh
