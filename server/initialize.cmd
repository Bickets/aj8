@ECHO OFF

set java=java
set vmargs=-Xms1G -Xmx1G -server -Dvisualvm.display.name=GameServer
set classpath=bin;assets/*;
set mainclass=org.apollo.Server

title Game Server

%java% %vmargs% -cp %classpath% %mainclass%

PAUSE