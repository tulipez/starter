call build.bat
cd ../vertx-server
"C:\Program Files\Java\openjdk-20\bin\java"^
 -server -Xms32M -Xmx5G -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m^
 -Xss10M -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true^
 --add-exports=java.desktop/sun.awt=ALL-UNNAMED^
 --add-exports=java.naming/com.sun.jndi.ldap=ALL-UNNAMED^
 --add-exports=java.naming/com.sun.jndi.url.ldap=ALL-UNNAMED^
 --add-exports=java.naming/com.sun.jndi.url.ldaps=ALL-UNNAMED^
 --add-opens=java.base/java.lang=ALL-UNNAMED^
 --add-opens=java.base/java.lang.invoke=ALL-UNNAMED^
 --add-opens=java.base/java.lang.reflect=ALL-UNNAMED^
 --add-opens=java.base/java.io=ALL-UNNAMED^
 --add-opens=java.base/java.security=ALL-UNNAMED^
 --add-opens=java.base/java.util=ALL-UNNAMED^
 --add-opens=java.base/java.util.concurrent=ALL-UNNAMED^
 --add-opens=java.management/javax.management=ALL-UNNAMED^
 --add-opens=java.naming/javax.naming=ALL-UNNAMED^
 -Djava.security.manager=allow^
 -jar starter-1.0.0-SNAPSHOT-fat.jar