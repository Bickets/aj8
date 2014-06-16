@ECHO OFF

set java=java
set vmargs=-Xms1G -Xmx1G
set mainclass=com.runescape.Game
title Game Client

%java% %vmargs% -cp %mainclass%

PAUSE