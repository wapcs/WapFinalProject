# WapFinalProject
Connecting dabase to tomcat, write this tag in 
C:\Program Files\Apache Software Foundation\Tomcat 8.5\conf\context.xml

```xml
   <Resource
    name="jdbc/taskdb"
    auth="Container"
    type="javax.sql.DataSource"
    maxActive="100"
    maxIdle="30"
    maxWait="10000"
    driverClassName="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost:3306/taskdb"
    username="root"
    password="pass"
    />
