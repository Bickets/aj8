@ECHO OFF

set java=java
set classpath=bin
set vmargs=-Xms1G -Xmx1G
set mainclass=com.runescape.Game
title Game Client

%java% %vmargs% -cp %classpath% %mainclass%

PAUSE