set JAVA_HOME=c:\Program Files\Java\jdk1.7.0_51\bin

"%JAVA_HOME%\java" -cp "c:\dev\appengine-java-sdk-1.9.9\lib\appengine-tools-api.jar" ^
    com.google.appengine.tools.KickStart ^
       com.google.appengine.tools.development.DevAppServerMain %*